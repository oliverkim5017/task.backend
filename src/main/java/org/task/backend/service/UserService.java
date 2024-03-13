package org.task.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.task.backend.model.dto.LoginDto;
import org.task.backend.model.dto.RegisterDto;
import org.task.backend.model.entity.User;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
public interface UserService extends IService<User> {
	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);

}
