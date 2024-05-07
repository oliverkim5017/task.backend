package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.task.backend.annotation.Permission;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.exception.UnauthorizedException;
import org.task.backend.model.dto.NodeDto;
import org.task.backend.model.dto.TaskDto;
import org.task.backend.model.entity.LoginUser;
import org.task.backend.model.entity.Project;
import org.task.backend.model.entity.Task;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.ProjectService;
import org.task.backend.service.TaskService;
import org.task.backend.service.UserService;
import org.task.backend.util.DateRangeParser;
import org.task.backend.util.JwtUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

	@DeleteMapping("/delTask/{id}")
	public Result delTask(@PathVariable Integer id) {
		boolean b = taskService.removeById(id);
		return b? Result.success("success"): Result.deleteFailed();
	}

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
		taskService.list(new QueryWrapper<Task>().lambda().eq(Task::getUserId, task.getUserId()))
				.forEach(t -> {
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
		return b? Result.success("success"): Result.saveFailed();

	}



	// endTime和startTime是区间时间
	@GetMapping("/getTasks")
	public Result getTasks(Integer projectId, LocalDateTime endTime, LocalDateTime startTime) {

		LambdaQueryWrapper<Task> lambdaQueryWrapper = new LambdaQueryWrapper<>();

		if (projectId != null) {
			lambdaQueryWrapper.eq(Task::getProjectId, projectId);
		}





	}



}
