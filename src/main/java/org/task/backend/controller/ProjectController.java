package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.task.backend.mapper.ProjectMapper;
import org.task.backend.model.entity.DateRange;
import org.task.backend.model.entity.Department;
import org.task.backend.model.entity.Project;
import org.task.backend.model.entity.User;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.DepartmentService;
import org.task.backend.service.ProjectService;
import org.task.backend.service.StatusService;
import org.task.backend.service.UserService;
import org.task.backend.util.DateRangeParser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-06
 */
@RestController
public class ProjectController {

	@Resource
	private ProjectService projectService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private UserService userService;
	@Resource
	private StatusService statusService;

	@GetMapping("/getProjects")
	public Result getProjects(String projectName, String startTime, String endTime, String createTime, String updateTime) {

		DateRange start = new DateRange();
		DateRange end = new DateRange();
		DateRange create = new DateRange();
		DateRange update = new DateRange();

		QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
		LambdaQueryWrapper<Project> lambdaQuery = queryWrapper.lambda().like(Project::getName, projectName);

		LocalDateTime[] localDateTimes = DateRangeParser.parseDateRange(startTime);
		if (localDateTimes.length > 0) {
			start.setStartDate(localDateTimes[0].toLocalDate());
			start.setEndDate(localDateTimes[1].toLocalDate());
		}

		localDateTimes = DateRangeParser.parseDateRange(endTime);
		if (localDateTimes.length > 0) {
			end.setStartDate(localDateTimes[0].toLocalDate());
			end.setEndDate(localDateTimes[1].toLocalDate());
		}

		localDateTimes = DateRangeParser.parseDateRange(createTime);
		if (localDateTimes.length > 0) {
			create.setStartDate(localDateTimes[0].toLocalDate());
			create.setEndDate(localDateTimes[1].toLocalDate());
		}
		if (localDateTimes.length > 0) {
			update.setStartDate(localDateTimes[0].toLocalDate());
			update.setEndDate(localDateTimes[1].toLocalDate());
		}

		if (start.getStartDate() != null && start.getEndDate() != null) {
			lambdaQuery.and(wrapper ->
					wrapper.between(Project::getStartTime, start.getStartDate(), start.getEndDate())
			);
		}
		if (end.getStartDate() != null && end.getEndDate() != null) {
			lambdaQuery.and(wrapper ->
					wrapper.between(Project::getEndTime, end.getStartDate(), end.getEndDate())
			);
		}
		if (create.getStartDate() != null && create.getEndDate() != null) {
			lambdaQuery.and(wrapper ->
					wrapper.between(Project::getCreateTime, create.getStartDate(), create.getEndDate())
			);
		}
		if (update.getStartDate() != null && update.getEndDate() != null){
			lambdaQuery.and(wrapper ->
					wrapper.between(Project::getUpdateTime, update.getStartDate(), update.getEndDate())
			);
		}

		List<Project> projects = projectService.list(lambdaQuery);
		List<Integer> departmentIds = projects.stream().map(Project::getDepartmentId).distinct().toList();
		if (!departmentIds.isEmpty()) {
			List<Department> departments = departmentService.list(new QueryWrapper<Department>().lambda().in(Department::getId, departmentIds));
			projects.forEach(project -> {
				Department department = departments.stream().filter(dept -> dept.getId().equals(project.getDepartmentId())).findFirst().orElse(null);
				project.setDepartment(department);
			});
		}

		List<Integer> userIds = new java.util.ArrayList<>(projects.stream().map(Project::getUserId).distinct().toList());
		userIds.addAll(projects.stream().map(Project::getApproveUserId).distinct().toList());


		if (userIds.isEmpty()) {
			return Result.success(projects);
		}

		List<User> users = userService.list(new QueryWrapper<User>().lambda().in(User::getId, userIds));
		projects.forEach(project -> {
			User user = users.stream().filter(u -> u.getId().equals(project.getUserId())).findFirst().orElse(null);
			project.setUser(user);
			User approveUser = users.stream().filter(u -> u.getId().equals(project.getApproveUserId())).findFirst().orElse(null);
			project.setApproveUser(approveUser);
		});

		return Result.success(projects);

	}



}
