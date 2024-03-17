package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.task.backend.annotation.Permission;
import org.task.backend.model.dto.LoginDto;
import org.task.backend.model.dto.RegisterDto;
import org.task.backend.model.entity.User;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.UserService;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@RestController
public class UserController {

	@Resource
	private UserService userService;

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

}
