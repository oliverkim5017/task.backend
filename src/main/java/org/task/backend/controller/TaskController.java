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
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.TaskNodeService;
import org.task.backend.service.TaskService;
import org.task.backend.util.DateRangeParser;
import org.task.backend.util.JwtUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
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


	@GetMapping("/getTaskById/{id}")
	public Result getTaskById(@PathVariable int id) {
		Task task = taskService.getTaskById(id);
		return task == null ? Result.error("任务不存在") : Result.success(task);
	}

	@GetMapping("/getTasks")
	public Result getTasks(@RequestParam(required = false) Integer teamId, @RequestParam(required = false) Integer stateId, @RequestParam(required = false) String dateRange) {
		LocalDate startTime = null;
		LocalDate endTime = null;
		if (dateRange != null && !dateRange.isBlank()) {
			LocalDateTime[] localDateTimes = DateRangeParser.parseDateRange(dateRange);
			startTime = localDateTimes[0].toLocalDate();
			endTime = localDateTimes[1].toLocalDate();
		}
		List<Task> tasks = taskService.getTasks(teamId, stateId, startTime, endTime);
		return Result.success(tasks);
	}


	@PostMapping("/addTask")
	@Permission({"管理员", "组长"})
	public Result addTask(@RequestBody @Validated TaskDto taskDto) {
		LoginUser loginUser = LoginThreadLocal.get();
		String token = loginUser.getToken();
		Claims claims = JwtUtil.getClaimsFromToken(token);
		Integer userId = claims.get("userId", Integer.class);
		Optional.ofNullable(userId).orElseThrow(UnauthorizedException::new);
		Task task = new Task();
		task.setCreatorId(userId);
		task.setContent(taskDto.getContent());
		task.setTeamId(taskDto.getTeamId());
		task.setStateId(taskDto.getStateId());
		boolean saved = taskService.save(task);
		return saved ? Result.success(taskService.getTaskById(task.getId())) : Result.saveFailed();
	}

	@PostMapping("/addNode")
	@Permission({"管理员", "组长"})
	@Transactional(rollbackFor = Exception.class)
	public Result addNode(@RequestBody @Validated NodeDto nodeDto) {

		long count = taskNodeService.count(new QueryWrapper<TaskNode>().lambda()
				.eq(TaskNode::getParentTaskId, nodeDto.getParentNodeId()));
		TaskNode taskNode = new TaskNode();
		taskNode.setParentTaskId(nodeDto.getParentNodeId());
		taskNode.setContent(nodeDto.getContent());
		taskNode.setUserId(nodeDto.getUserId());
		taskNode.setStateId(nodeDto.getStateId());
		taskNode.setNodeIndex((int) count);
		Integer userId = LoginThreadLocal.getUserId();
		Optional.ofNullable(userId).orElseThrow(UnauthorizedException::new);
		taskNode.setCreatorId(userId);
		boolean saved = taskNodeService.save(taskNode);
		return saved ? Result.success() : Result.saveFailed();
	}


}
