package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.mapper.ProjectMapper;
import org.task.backend.model.entity.*;
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
	public Result getProjects(String name, String startTime, String endTime, String createTime, String updateTime) {

		DateRange start = new DateRange();
		DateRange end = new DateRange();
		DateRange create = new DateRange();
		DateRange update = new DateRange();

		LambdaQueryWrapper<Project> lambdaQueryWrapper = new LambdaQueryWrapper<>();

		if (name != null && !name.isBlank()) {
			lambdaQueryWrapper.like(Project::getName, name);
		}

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
			lambdaQueryWrapper.and(wrapper ->
					wrapper.between(Project::getStartTime, start.getStartDate(), start.getEndDate())
			);
		}
		if (end.getStartDate() != null && end.getEndDate() != null) {
			lambdaQueryWrapper.and(wrapper ->
					wrapper.between(Project::getEndTime, end.getStartDate(), end.getEndDate())
			);
		}
		if (create.getStartDate() != null && create.getEndDate() != null) {
			lambdaQueryWrapper.and(wrapper ->
					wrapper.between(Project::getCreateTime, create.getStartDate(), create.getEndDate())
			);
		}
		if (update.getStartDate() != null && update.getEndDate() != null){
			lambdaQueryWrapper.and(wrapper ->
					wrapper.between(Project::getUpdateTime, update.getStartDate(), update.getEndDate())
			);
		}

		List<Project> projects = projectService.list(lambdaQueryWrapper);
		List<Integer> departmentIds = projects.stream().map(Project::getDepartmentId).distinct().toList();
		if (!departmentIds.isEmpty()) {
			List<Department> departments = departmentService.list(new QueryWrapper<Department>().lambda().in(Department::getId, departmentIds));
			projects.forEach(project -> {
				Department department = departments.stream().filter(dept -> dept.getId().equals(project.getDepartmentId())).findFirst().orElse(null);
				project.setDepartment(department);
			});
		}


		List<User> users = userService.list();
		projects.forEach(project -> {
			User user = users.stream().filter(u -> u.getId().equals(project.getUserId())).findFirst().orElse(null);
			project.setUser(user);
			User approveUser = users.stream().filter(u -> u.getId().equals(project.getApproveUserId())).findFirst().orElse(null);
			project.setApproveUser(approveUser);
			User createdBY = users.stream().filter(u -> u.getId().equals(project.getCreatedBy())).findFirst().orElse(null);
			project.setCreator(createdBY);
			User updatedBy = users.stream().filter(u -> u.getId().equals(project.getUpdatedBy())).findFirst().orElse(null);
			project.setUpdater(updatedBy);
		});

		return Result.success(projects);

	}

	@PostMapping("/saveProject")
	public Result saveProject(@RequestBody Project project) {
		if (project.getDepartmentId() == 0) {
			return Result.error("请选择负责部门");
		}
		if (project.getUserId() == 0) {
			return Result.error("请选择负责人");
		}
		if (project.getApproveUserId() == 0) {
			return Result.error("请选择审批人");
		}
		if (project.getStatusId() == 0) {
			Status status = statusService.getOne(new QueryWrapper<Status>().lambda().eq(Status::isDefaultStatus, true));
			project.setStatusId(status.getId());
		}
		Integer userId = LoginThreadLocal.getUserId();
		project.setUpdatedBy(userId);
		if (project.getId() == null) {
			project.setCreatedBy(userId);
		}
		boolean saved = projectService.saveOrUpdate(project);
		return saved? Result.success("success") : Result.saveFailed();
	}

	@DeleteMapping("/delProject/{id}")
	public Result delProject(@PathVariable Integer id) {
		boolean removed = projectService.removeById(id);
		return removed? Result.success("success") : Result.deleteFailed();
	}

}
