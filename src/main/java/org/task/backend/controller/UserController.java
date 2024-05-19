package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.task.backend.annotation.Permission;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.model.dto.ChangePasswordDto;
import org.task.backend.model.dto.LoginDto;
import org.task.backend.model.dto.RegisterDto;
import org.task.backend.model.dto.UserSelectDto;
import org.task.backend.model.entity.Department;
import org.task.backend.model.entity.LoginUser;
import org.task.backend.model.entity.Role;
import org.task.backend.model.entity.User;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.DepartmentService;
import org.task.backend.service.RoleService;
import org.task.backend.service.TeamService;
import org.task.backend.service.UserService;
import org.task.backend.util.JwtUtil;
import org.task.backend.util.SHA256Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@RestController
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private TeamService teamService;
	@Resource
	private DepartmentService departmentService;

	@GetMapping("/myInfo")
	public Result myInfo() {
		LoginUser loginUser = LoginThreadLocal.get();
		String token = loginUser.getToken();
		Claims claims = JwtUtil.getClaimsFromToken(token);
		int userId = (int) claims.get("userId");
		User user = userService.getUserById(userId);
		roleService.getOptById(user.getRoleId()).ifPresent(user::setRole);
		departmentService.getOptById(user.getDepartmentId()).ifPresent(user::setDepartment);
		return Result.success(user);
	}

	@PostMapping("/login")
	public Result login(@RequestBody @Validated LoginDto loginDto) {
		String token = userService.login(loginDto);
		return token == null ? Result.error("用户名或密码错误") : Result.success("success",token);
	}

	@PostMapping("/register")
	public Result register(@RequestBody @Validated RegisterDto registerDto) {
		String token = userService.register(registerDto);
		return token == null ? Result.error("注册失败") : Result.success("success",token);
	}

	@Permission("管理员")
	@PutMapping("/changeRole/{userId}/{roleId}")
	public Result changeRole(@PathVariable int userId, @PathVariable int roleId) {
		User user = userService.getById(userId);
		if (user == null) {
			return Result.error("用户不存在");
		}
		user.setRoleId(roleId);
		boolean updated = userService.updateById(user);
		return updated? Result.success(user) : Result.updateFailed();
	}

	@Permission("管理员")
	@DeleteMapping("/delUser/{id}")
	public Result delUser(@PathVariable int id) {
		boolean removed = userService.removeById(id);
		return removed? Result.success("success") : Result.deleteFailed();
	}

	@GetMapping("/getUsers")
	public Result getUsers() {
		List<User> users = userService.list();
		ArrayList<UserSelectDto> userSelectDtos = new ArrayList<>();
		users.forEach(user -> {
			UserSelectDto userSelectDto = new UserSelectDto();
			userSelectDto.setId(user.getId());
			userSelectDto.setName(user.getName());
			userSelectDto.setDepartmentId(user.getDepartmentId());
			userSelectDtos.add(userSelectDto);
		});
		return Result.success(userSelectDtos);
	}

	@GetMapping("/getUser")
	public Result getUser(String username, String name, Integer departmentId) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		if (username != null && !username.isBlank()) {
			wrapper.lambda().like(User::getUsername, username);
		}
		if (name != null && !name.isBlank()) {
			wrapper.lambda().like(User::getName, name);
		}
		if (departmentId != null) {
			wrapper.lambda().eq(User::getDepartmentId, departmentId);
		}
		List<User> users = userService.list(wrapper);
		if (users.size() == 0)
		{
			return Result.success();
		}

		List<Integer> departmentIds = users.stream().map(User::getDepartmentId).distinct().toList();
		List<Integer> roleIds = users.stream().map(User::getRoleId).distinct().toList();
		departmentService.list(new QueryWrapper<Department>().lambda().in(Department::getId, departmentIds)).forEach(department -> {
			users.forEach(user -> {
				if (user.getDepartmentId().equals(department.getId())) {
					user.setDepartment(department);
				}
			});
		});
		roleService.list(new QueryWrapper<Role>().lambda().in(Role::getId, roleIds)).forEach(role -> {
			users.forEach(user -> {
				if (user.getRoleId().equals(role.getId())) {
					user.setRole(role);
				}
			});
		});
		return Result.success(users);
	}

	@PostMapping("/saveUser")
	public Result saveUser(@RequestBody User user) {
		if (user.getRoleId() == null) {
			return Result.error("请选择角色");
		}
		if (user.getDepartmentId() == null) {
			return Result.error("请选择部门");
		}
		boolean saved = userService.saveOrUpdate(user);
		return saved? Result.success("success") : Result.saveFailed();
	}


	@PostMapping("/changePassword")
	public Result changePassword(@RequestBody @Validated ChangePasswordDto changePasswordDto) {
		Integer userId = LoginThreadLocal.getUserId();
		User user = userService.getById(userId);
		LoginDto loginDto = new LoginDto();
		loginDto.setUsername(user.getUsername());
		loginDto.setPassword(changePasswordDto.getOldPassword());
		String newToken = userService.login(loginDto);
		if (newToken == null) {
			return Result.error("旧密码错误");
		}

		String hashPassword = SHA256Util.hashPassword(changePasswordDto.getNewPassword());
		user.setPassword(hashPassword);
		boolean changed = userService.updateById(user);
		return changed? Result.success("success") : Result.error("用户名或密码错误");
	}

}
