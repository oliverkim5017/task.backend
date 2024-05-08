package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.task.backend.annotation.Operation;
import org.task.backend.model.dto.ChartDto;
import org.task.backend.model.entity.*;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-06
 */
@RestController
public class ApiController {

	@Resource
	private DepartmentService departmentService;
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;
	@Resource
	private StatusService statusService;
	@Resource
	private ProjectService projectService;
	@Resource
	private OperationLogService operationLogService;
	@Resource
	private TaskService taskService;


	@GetMapping("/getDepartments")
	public Result getDepartments() {
		List<Department> departments = departmentService.list();
		return Result.success(departments);
	}

	@Operation("编辑部门")
	@PostMapping("/saveOrUpdateDept")
	public Result saveOrUpdateDept(@RequestBody Department department) {
		boolean save = departmentService.saveOrUpdate(department);
		return save ? Result.success("success") : Result.saveFailed();
	}

	@GetMapping("/getRoles")
	public Result getRoles(String name) {
		QueryWrapper<Role> wrapper = new QueryWrapper<>();
		if (name != null && !name.isBlank()) {
			wrapper.lambda().like(Role::getName, name);
		}
		List<Role> roles = roleService.list(wrapper);
		return Result.success(roles);
	}

	@Operation("编辑角色")
	@PostMapping("/saveRole")
	public Result saveRole(@RequestBody Role role) {
		if (role.isDefaultRole()) {
			boolean b = roleService.resetDefaultRole();
			if (!b) {
				return Result.updateFailed();
			}
		}
		boolean saved = roleService.saveOrUpdate(role);
		return saved ? Result.success("success") : Result.saveFailed();
	}

	@Operation("删除角色")
	@DeleteMapping("/delRole/{id}")
	public Result delRole(@PathVariable int id) {
		List<User> list = userService.list(new QueryWrapper<User>().lambda().eq(User::getRoleId, id));
		if (!list.isEmpty()) {
			return Result.error("该角色下有用户，无法删除");
		}
		boolean removed = roleService.removeById(id);
		return removed ? Result.success("success") : Result.deleteFailed();
	}

	@GetMapping("/getStatus")
	public Result getStatus(String name, String forTask) {

		QueryWrapper<Status> wrapper = new QueryWrapper<>();
		if (name != null && !name.isBlank()) {
			wrapper.lambda().like(Status::getName, name);
		}

		if (forTask != null && !forTask.isBlank()) {
			wrapper.lambda().eq(Status::isForTask, Boolean.parseBoolean(forTask));
		}

		List<Status> list = statusService.list(wrapper);
		return Result.success(list);
	}

	@Operation("编辑项目状态")
	@PostMapping("/saveStatus")
	public Result saveStatus(@RequestBody Status status) {
		if (status.isDefaultStatus()) {
			boolean b = statusService.resetDefaultStatus(status.isForTask());
			if (!b) {
				return Result.updateFailed();
			}
		}
		boolean saved = statusService.saveOrUpdate(status);
		return saved ? Result.success("success") : Result.saveFailed();
	}

	@Operation("删除项目状态")
	@DeleteMapping("/delStatus/{id}")
	public Result delStatus(@PathVariable int id) {
		List<Project> list = projectService.list(new QueryWrapper<Project>().lambda().eq(Project::getStatusId, id));
		if (!list.isEmpty()) {
			return Result.error("该状态被使用中，无法删除");
		}
		boolean removed = statusService.removeById(id);
		return removed ? Result.success("success") : Result.deleteFailed();
	}

	@Operation("删除部门")
	@DeleteMapping("/delDept/{id}")
	public Result delDept(@PathVariable int id) {
		List<User> list = userService.list(new QueryWrapper<User>().lambda().eq(User::getDepartmentId, id));
		if (!list.isEmpty()) {
			return Result.error("该部门下有用户，无法删除");
		}
		boolean removed = departmentService.removeDeptById(id);
		return removed ? Result.success("success") : Result.deleteFailed();
	}

	@GetMapping("/getOperationLog")
	public Result getOperationLog() {
		List<OperationLog> operationLogs = operationLogService.getLogs();
		return Result.success(operationLogs);
	}

	@GetMapping("/getProjectSummary")
	public Result getProjectSummary(int year, int month) {
		// 查询当月结束的项目
		LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(year, month, 1, 0, 0, 0).plusMonths(1);
		List<Project> projects = projectService.list(new QueryWrapper<Project>().lambda()
				.between(Project::getEndTime, start, end)
		);
		List<Status> statuses = statusService.list(new QueryWrapper<Status>().lambda()
				.eq(Status::isForTask, false));
		ArrayList<ChartDto> chartDtos = new ArrayList<>();
		statuses.forEach(status -> {
			ChartDto chartDto = new ChartDto();
			chartDto.setName(status.getName());
			long count = projects.stream().filter(project -> project.getStatusId() == (status.getId())).count();
			chartDto.setValue((int) count);
			chartDtos.add(chartDto);
		});
		return Result.success(chartDtos);
	}

	@GetMapping("/getTaskSummary")
	public Result getTaskSummary(int year, int month) {
		// 查询当月结束的任务
		LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(year, month, 1, 0, 0, 0).plusMonths(1);
		List<Task> tasks = taskService.list(new QueryWrapper<Task>().lambda()
				.between(Task::getEndTime, start, end)
		);
		List<Status> statuses = statusService.list(new QueryWrapper<Status>().lambda()
				.eq(Status::isForTask, true));
		ArrayList<ChartDto> chartDtos = new ArrayList<>();
		statuses.forEach(status -> {
			ChartDto chartDto = new ChartDto();
			chartDto.setName(status.getName());
			long count = tasks.stream().filter(task -> task.getStateId() == (status.getId())).count();
			chartDto.setValue((int) count);
			chartDtos.add(chartDto);
		});
		return Result.success(chartDtos);
	}

	@GetMapping("/getProjectOfDepartmentSummary")
	public Result getProjectOfDepartmentSummary(int year, int month) {
		// 查询当月结束的任务,按照一级部门分组
		LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(year, month, 1, 0, 0, 0).plusMonths(1);
		List<Project> projects = projectService.list();
		List<Department> departments = departmentService.list();
		ArrayList<ChartDto> chartDtos = new ArrayList<>();
		departments.forEach(department -> {
			ChartDto chartDto = new ChartDto();
			chartDto.setName(department.getName());
			long count = projects.stream().filter(project -> project.getDepartmentId() == (department.getId())).count();
			chartDto.setValue((int) count);
			chartDtos.add(chartDto);
		});
		return Result.success(chartDtos);
	}

}
