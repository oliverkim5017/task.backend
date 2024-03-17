package org.task.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.task.backend.mapper.UserMapper;
import org.task.backend.model.dto.LoginDto;
import org.task.backend.model.dto.RegisterDto;
import org.task.backend.model.entity.Role;
import org.task.backend.model.entity.User;
import org.task.backend.service.RoleService;
import org.task.backend.service.UserService;
import org.task.backend.util.JwtUtil;
import org.task.backend.util.SHA256Util;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleService roleService;

	@Override
	public String login(LoginDto loginDto) {
		User user = userMapper.selectOne(new QueryWrapper<User>().lambda()
				.eq(User::getUsername, loginDto.getUsername()));
		if (user == null) {
			throw new RuntimeException("用户不存在");
		}
		String hashedPassword = SHA256Util.hashPassword(loginDto.getPassword());
		if (!user.getPassword().equals(hashedPassword)) {
			throw new RuntimeException("用户名或密码错误");
		}
		return JwtUtil.generateToken(user.getId(), user.getUsername(), user.getName(), user.getTeamId(), user.getRoleId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String register(RegisterDto registerDto) {
		User existUser = userMapper.selectOne(new QueryWrapper<User>().lambda()
				.eq(User::getUsername, registerDto.getUsername()));
		if (existUser != null) {
			throw new RuntimeException("用户名已被存在");
		}
		String hashedPassword = SHA256Util.hashPassword(registerDto.getPassword());
		User user = new User();
		user.setUsername(registerDto.getUsername());
		user.setPassword(hashedPassword);
		user.setName(registerDto.getName());
		user.setTeamId(registerDto.getTeamId());
		Role role = roleService.getOne(new QueryWrapper<Role>().lambda()
				.eq(Role::isDefaultRole, true));
		user.setRoleId(role.getId());
		int affectedRow = userMapper.insert(user);
		if (affectedRow == 0) {
			throw new RuntimeException("注册失败");
		}
		return JwtUtil.generateToken(user.getId(), user.getUsername(), user.getName(), user.getTeamId(), user.getRoleId());
	}


	@Override
	public String getRole(String token) {
		Claims claims = JwtUtil.getClaimsFromToken(token);
		Integer roleId = claims.get("roleId", Integer.class);
		Role role = roleService.getById(roleId);
		return role.getName();
	}
}
