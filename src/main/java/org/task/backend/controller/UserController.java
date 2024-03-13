package org.task.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.task.backend.model.dto.LoginDto;
import org.task.backend.model.dto.RegisterDto;
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
	public Result login(@RequestBody LoginDto loginDto) {
		String token = userService.login(loginDto);
		return token == null ? Result.error("用户名或密码错误") : Result.success("success",token);
	}

	@PostMapping("/register")
	public Result register(@RequestBody RegisterDto registerDto) {
		String token = userService.register(registerDto);
		return token == null ? Result.error("注册失败") : Result.success("success",token);
	}

}
