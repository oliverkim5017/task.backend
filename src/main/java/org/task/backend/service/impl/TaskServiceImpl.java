package org.task.backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.task.backend.mapper.TaskMapper;
import org.task.backend.model.entity.Task;
import org.task.backend.model.entity.TaskNode;
import org.task.backend.model.entity.TaskState;
import org.task.backend.model.entity.User;
import org.task.backend.service.TaskService;
import org.springframework.stereotype.Service;
import org.task.backend.service.TaskStateService;
import org.task.backend.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
* @author 18200
* @description 针对表【task】的数据库操作Service实现
* @createDate 2024-03-16 17:59:13
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService{

	@Resource
	private TaskMapper taskMapper;
	@Resource
	private UserService userService;
	@Resource
	private TaskStateService taskStateService;

	@Override
	public Task getTaskById(Integer id) {
		Task task = taskMapper.getTaskById(id);
		Map<Integer, User> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, user -> user));
		Map<Integer, TaskState> stateMap = taskStateService.list().stream().collect(Collectors.toMap(TaskState::getId, taskState -> taskState));
		setUserAndState(task, userMap, stateMap);
		return task;
	}

	@Override
	public List<Task> getTasks(List<Integer>  teamIds, List<Integer>  stateIds, LocalDate startTime, LocalDate endTime) {
		List<Task> tasks = taskMapper.getTasks(teamIds, stateIds, startTime, endTime);
		Map<Integer, User> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, user -> user));
		Map<Integer, TaskState> stateMap = taskStateService.list().stream().collect(Collectors.toMap(TaskState::getId, taskState -> taskState));
		for (Task task : tasks) {
			setUserAndState(task, userMap, stateMap);
		}
		return tasks;
	}

	private void setUserAndState(Task task, Map<Integer, User> userMap, Map<Integer, TaskState> stateMap) {
		task.setCreator(userMap.get(task.getCreatorId()));
		task.setState(stateMap.get(task.getStateId()));
		for (TaskNode node : task.getNodes()) {
			node.setAssignedUser(userMap.get(node.getUserId()));
			node.setCreator(userMap.get(node.getCreatorId()));
			node.setTaskState(stateMap.get(node.getStateId()));
		}
	}
}




