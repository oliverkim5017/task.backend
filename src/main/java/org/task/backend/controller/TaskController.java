package org.task.backend.controller;

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
import org.task.backend.model.entity.Task;
import org.task.backend.model.entity.TaskNode;
import org.task.backend.model.entity.TaskState;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.TaskNodeService;
import org.task.backend.service.TaskService;
import org.task.backend.service.TaskStateService;
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
	private TaskNodeService taskNodeService;
	@Resource
	private TaskStateService taskStateService;


	@GetMapping("/getTaskById/{id}")
	public Result getTaskById(@PathVariable int id) {
		Task task = taskService.getTaskById(id);
		return task == null ? Result.error("任务不存在") : Result.success(task);
	}

	@GetMapping("/getTasks")
	public Result getTasks(@RequestParam(required = false) String teamIds, @RequestParam(required = false) String stateIds, @RequestParam(required = false) String dateRange) {
		LocalDate startTime = null;
		LocalDate endTime = null;
		List<Integer> teamIdList = new ArrayList<>();
		List<Integer> stateIdList = new ArrayList<>();
		if (dateRange != null && !dateRange.isBlank()) {
			LocalDateTime[] localDateTimes = DateRangeParser.parseDateRange(dateRange);
			startTime = localDateTimes[0].toLocalDate();
			endTime = localDateTimes[1].toLocalDate();
		}
		if (teamIds != null && !teamIds.isBlank()) {
			teamIdList = Arrays.stream(teamIds.split(",")).map(Integer::parseInt).toList();
		}
		if (stateIds != null && !stateIds.isBlank()) {
			stateIdList = Arrays.stream(stateIds.split(",")).map(Integer::parseInt).toList();
		}
		List<Task> tasks = taskService.getTasks(teamIdList, stateIdList, startTime, endTime);
		return Result.success(tasks);
	}

	@GetMapping("/getStates")
	public Result getStates() {
		List<TaskState> states = taskStateService.list();
		return Result.success(states);
	}


	@PostMapping("/addTask")
	@Permission({"管理员", "组长"})
	public Result addTask(@RequestBody @Validated TaskDto taskDto) {
		LoginUser loginUser = LoginThreadLocal.get();
		String token = loginUser.getToken();
		Claims claims = JwtUtil.getClaimsFromToken(token);
		Integer userId = claims.get("userId", Integer.class);
		Optional.ofNullable(userId).orElseThrow(UnauthorizedException::new);
		Task task = new Task(taskDto);
		task.setCreatorId(userId);
		task.setContent(taskDto.getContent());
		task.setTeamId(taskDto.getTeamId());
		task.setStateId(taskDto.getStateId());
		task.setDeadLine(taskDto.getDeadLine());
		boolean saved = taskService.save(task);
		return saved ? Result.success(taskService.getTaskById(task.getId())) : Result.saveFailed();
	}

	@PostMapping("/addNode")
	@Permission({"管理员", "组长"})
	@Transactional(rollbackFor = Exception.class)
	public Result addNode(@RequestBody @Validated NodeDto nodeDto) {

		long count = taskNodeService.count(new QueryWrapper<TaskNode>().lambda()
				.eq(TaskNode::getParentTaskId, nodeDto.getParentTaskId()));
		TaskNode taskNode = new TaskNode();
		taskNode.setParentTaskId(nodeDto.getParentTaskId());
		taskNode.setContent(nodeDto.getContent());
		taskNode.setUserId(nodeDto.getUserId());
		taskNode.setStateId(nodeDto.getStateId());
		taskNode.setNodeIndex((int) count);
		taskNode.setDeadLine(nodeDto.getDeadLine());
		Integer userId = LoginThreadLocal.getUserId();
		Optional.ofNullable(userId).orElseThrow(UnauthorizedException::new);
		taskNode.setCreatorId(userId);
		boolean saved = taskNodeService.save(taskNode);
		return saved ? Result.success() : Result.saveFailed();
	}


	@Permission({"管理员", "组长"})
	@DeleteMapping("/delTaskNode/{id}")
	public Result delTaskNode(@PathVariable int id) {
		boolean removed = taskNodeService.removeById(id);
		return removed ? Result.success() : Result.deleteFailed();
	}

	@Permission({"管理员", "组长"})
	@PutMapping("/updateTask")
	public Result updateTask(@RequestBody TaskDto taskDto) {
		Task task = new Task(taskDto);
		boolean updated = taskService.updateById(task);
		if (updated) {
			return Result.success(taskService.getTaskById(task.getId()));
		} else {
			return Result.updateFailed();
		}
	}

	@Permission({"管理员", "组长"})
	@DeleteMapping("/delTask/{id}")
	public Result delTask(@PathVariable int id) {
		boolean removed = taskService.removeTaskById(id);
		return removed ? Result.success() : Result.deleteFailed();
	}

	@Permission({"管理员", "组长"})
	@PutMapping("/updateNode")
	public Result updateNode(@RequestBody NodeDto nodeDto) {
		TaskNode taskNode = new TaskNode(nodeDto);
		taskNodeService.updateById(taskNode);
		return Result.success();
	}

	@GetMapping("/getMyTasks")
	public Result getMyTasks() {
		LoginUser loginUser = LoginThreadLocal.get();
		String token = loginUser.getToken();
		Claims claims = JwtUtil.getClaimsFromToken(token);
		Integer userId = claims.get("userId", Integer.class);
		Optional.ofNullable(userId).orElseThrow(UnauthorizedException::new);
		List<TaskNode> taskNodes =  taskNodeService.getTaskNodes(userId);
		return Result.success(taskNodes);
	}

}
