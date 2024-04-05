package org.task.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.task.backend.model.dto.LoginDto;
import org.task.backend.model.dto.RegisterDto;
import org.task.backend.model.entity.User;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
public interface UserService extends IService<User> {
	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);

	String getRole(String token);

	User getUserById(Integer id);
}
