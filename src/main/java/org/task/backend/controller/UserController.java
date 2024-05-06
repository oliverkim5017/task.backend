package org.task.backend.controller;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.task.backend.annotation.Permission;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.model.dto.LoginDto;
import org.task.backend.model.dto.RegisterDto;
import org.task.backend.model.dto.UserSelectDto;
import org.task.backend.model.entity.LoginUser;
import org.task.backend.model.entity.User;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.DepartmentService;
import org.task.backend.service.RoleService;
import org.task.backend.service.TeamService;
import org.task.backend.service.UserService;
import org.task.backend.util.JwtUtil;

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
			userSelectDto.setTeamId(user.getDepartmentId());
			userSelectDtos.add(userSelectDto);
		});
		return Result.success(userSelectDtos);
	}

}
