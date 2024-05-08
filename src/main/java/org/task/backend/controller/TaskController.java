package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.task.backend.annotation.Operation;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.model.dto.TaskDto;
import org.task.backend.model.entity.Project;
import org.task.backend.model.entity.Status;
import org.task.backend.model.entity.Task;
import org.task.backend.model.entity.User;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.ProjectService;
import org.task.backend.service.StatusService;
import org.task.backend.service.TaskService;
import org.task.backend.service.UserService;
import org.task.backend.util.DateRangeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-16
 */
@RestController
public class TaskController {

	@Resource
	private TaskService taskService;
	@Resource
	private ProjectService projectService;
	@Resource
	private UserService userService;
	@Resource
	private StatusService statusService;

	@DeleteMapping("/delTask/{id}")
	public Result delTask(@PathVariable Integer id) {
		boolean b = taskService.removeById(id);
		return b ? Result.success("success") : Result.deleteFailed();
	}

	@GetMapping("/getTaskById/{id}")
	public Result getTaskById(@PathVariable int id) {
		Task task = taskService.getById(id);
		if (task == null) {
			return Result.error("任务不存在");
		}
		Status status = statusService.getById(task.getStateId());
		task.setState(status);
		return Result.success(task);

	}

	@Operation("编辑任务")
	@PostMapping("/saveTask")
	public Result saveTask(@RequestBody Task task) {
		if (task.getContent() == null || task.getContent().isBlank()) {
			return Result.error("任务内容不能为空");
		}
		if (task.getStartTime().isAfter(task.getEndTime())) {
			return Result.error("任务开始时间不能晚于结束时间");
		}
		if (task.getEndTime().isBefore(LocalDateTime.now())) {
			return Result.error("任务结束时间不能早于当前时间");
		}
		if (task.getUserId() == null) {
			return Result.error("任务负责人不能为空");
		}
		if (task.getApproveUserId() == null) {
			return Result.error("审批人不能为空");
		}
		if (task.getProjectId() == null) {
			return Result.error("须指定项目");
		}
		if (task.getStateId() == 0) {
			return Result.error("任务状态不能为空");
		}
		if (task.getId() == null) {
			Integer userId = LoginThreadLocal.getUserId();
			task.setCreatorId(userId);
		}
		Project project = projectService.getById(task.getProjectId());
		if (project == null) {
			return Result.error("项目不存在");
		}
		if (task.getEndTime().isAfter(project.getEndTime().atTime(23, 59, 59))) {
			return Result.error("任务结束时间不能晚于项目结束时间");
		}
		if (task.getStartTime().isBefore(project.getStartTime().atTime(0, 0, 0))) {
			return Result.error("任务开始时间不能早于项目开始时间");
		}
		if (task.getParentId() != null) {
			Task parent = taskService.getById(task.getParentId());
			if (parent == null) {
				return Result.error("父任务不存在");
			}
			if (!parent.getProjectId().equals(task.getProjectId())) {
				return Result.error("父任务不属于同一项目");
			}
			if (parent.getStartTime().isAfter(task.getStartTime())) {
				return Result.error("父任务开始时间不能晚于子任务开始时间");
			}
			if (parent.getEndTime().isBefore(task.getEndTime())) {
				return Result.error("父任务结束时间不能晚于子任务结束时间");
			}
		}
		List<Task> tasks = taskService.list(new QueryWrapper<Task>().lambda().eq(Task::getUserId, task.getUserId()));
		tasks.removeIf(t -> t.getId().equals(task.getId()));
		tasks.forEach(t -> {
			if (t.getStartTime().isBefore(task.getEndTime()) && t.getEndTime().isAfter(task.getStartTime())) {
				throw new RuntimeException("同一时间段只能有一个任务");
			}
			if (t.getStartTime().isAfter(task.getStartTime()) && t.getStartTime().isBefore(task.getEndTime())) {
				throw new RuntimeException("同一时间段只能有一个任务");
			}
			if (t.getEndTime().isAfter(task.getStartTime()) && t.getEndTime().isBefore(task.getEndTime())) {
				throw new RuntimeException("同一时间段只能有一个任务");
			}
		});

		boolean b = taskService.saveOrUpdate(task);
		return b ? Result.success("success") : Result.saveFailed();

	}


	@GetMapping("/getTasks")
	public Result getTasks(Integer projectId, String startTime, String endTime) {

		LambdaQueryWrapper<Task> lambdaQueryWrapper = new LambdaQueryWrapper<>();

		if (projectId != null) {
			lambdaQueryWrapper.eq(Task::getProjectId, projectId);
		}

		if (startTime != null) {
			LocalDate[] localDateTimes = DateRangeParser.parseDateRange(startTime);
			if (localDateTimes.length > 0) {
				lambdaQueryWrapper.between(Task::getStartTime, localDateTimes[0], localDateTimes[1]);
			}
		}

		if (endTime != null) {
			LocalDate[] localDateTimes = DateRangeParser.parseDateRange(endTime);
			if (localDateTimes.length > 0) {
				lambdaQueryWrapper.between(Task::getEndTime, localDateTimes[0], localDateTimes[1]);
			}
		}


		List<Task> tasks = taskService.list(lambdaQueryWrapper);

		if (tasks.isEmpty()) {
			return Result.success(tasks);
		}

		List<Integer> userIds = tasks.stream().map(Task::getUserId).distinct().toList();
		List<Integer> approveUserIds = tasks.stream().map(Task::getApproveUserId).distinct().toList();
		List<Integer> projectIds = tasks.stream().map(Task::getProjectId).distinct().toList();
		List<Integer> creatorIds = tasks.stream().map(Task::getCreatorId).distinct().toList();
		List<Integer> ids = new ArrayList<>();
		ids.addAll(userIds);
		ids.addAll(approveUserIds);
		ids.addAll(creatorIds);
		List<User> list = userService.list(new QueryWrapper<User>().lambda().in(User::getId, ids));
		List<Project> projectList = projectService.list(new QueryWrapper<Project>().lambda().in(Project::getId, projectIds));
		tasks.forEach(task -> {
			Optional<User> user = list.stream().filter(u -> u.getId().equals(task.getUserId())).findFirst();
			user.ifPresent(task::setUser);
			Optional<User> approveUser = list.stream().filter(u -> u.getId().equals(task.getApproveUserId())).findFirst();
			approveUser.ifPresent(task::setApproveUser);
			Optional<Project> project = projectList.stream().filter(p -> p.getId().equals(task.getProjectId())).findFirst();
			project.ifPresent(task::setProject);
			Optional<User> creator = list.stream().filter(u -> u.getId().equals(task.getCreatorId())).findFirst();
			creator.ifPresent(task::setCreator);
		});
		return Result.success(tasks);


	}

	@GetMapping("/getMyTasks")
	public Result getMyTasks() {
		Integer userId = LoginThreadLocal.getUserId();

		LocalDateTime now = LocalDateTime.now();

		// 只找已经开始的——开始时间早于现在
		List<Task> tasks = taskService.list(new QueryWrapper<Task>().lambda().eq(Task::getUserId, userId)
				.le(Task::getStartTime, now));

		List<Status> statuses = statusService.list();
		tasks.forEach(task -> {
			Optional<Status> status = statuses.stream().filter(s -> s.getId().equals(task.getStateId())).findFirst();
			status.ifPresent(task::setState);
		});
		tasks.removeIf(task -> task.getState().isForFinish());

		return Result.success(tasks);
	}





}
