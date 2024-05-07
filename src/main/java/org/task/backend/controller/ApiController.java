package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.task.backend.model.entity.*;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.*;

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



	@GetMapping("/getDepartments")
	public Result getDepartments() {
		List<Department> departments = departmentService.list();
		return Result.success(departments);
	}

	@PostMapping("/saveOrUpdateDept")
	public Result saveOrUpdateDept(@RequestBody Department department) {
		boolean save = departmentService.saveOrUpdate(department);
		return save? Result.success("success"): Result.saveFailed();
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

	@PostMapping("/saveRole")
	public Result saveRole(@RequestBody Role role) {
		if (role.isDefaultRole()) {
			boolean b = roleService.resetDefaultRole();
			if (!b) {
				return Result.updateFailed();
			}
		}
		boolean saved = roleService.saveOrUpdate(role);
		return saved? Result.success("success"): Result.saveFailed();
	}

	@DeleteMapping("/delRole/{id}")
	public Result delRole(@PathVariable int id) {
		List<User> list = userService.list(new QueryWrapper<User>().lambda().eq(User::getRoleId, id));
		if (!list.isEmpty()) {
			return Result.error("该角色下有用户，无法删除");
		}
		boolean removed = roleService.removeById(id);
		return removed? Result.success("success"): Result.deleteFailed();
	}

	@GetMapping("/getStatus")
	public Result getStatus() {
		List<Status> list = statusService.list();
		return Result.success(list);
	}

	@PostMapping("/saveStatus")
	public Result saveStatus(@RequestBody Status status) {
		if (status.isDefaultStatus()) {
			boolean b = statusService.resetDefaultStatus();
			if (!b) {
				return Result.updateFailed();
			}
		}
		boolean saved = statusService.saveOrUpdate(status);
		return saved? Result.success("success"): Result.saveFailed();
	}

	@DeleteMapping("/delStatus/{id}")
	public Result delStatus(@PathVariable int id) {
		List<Project> list = projectService.list(new QueryWrapper<Project>().lambda().eq(Project::getStatusId, id));
		if (!list.isEmpty()) {
			return Result.error("该状态被使用中，无法删除");
		}
		boolean removed = statusService.removeById(id);
		return removed? Result.success("success"): Result.deleteFailed();
	}


}
