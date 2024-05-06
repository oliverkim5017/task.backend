package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.task.backend.mapper.RoleMapper;
import org.task.backend.model.entity.Role;
import org.task.backend.service.RoleService;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Resource
	private RoleMapper roleMapper;


	@Override
	public boolean resetDefaultRole() {
		int i = roleMapper.resetDefaultRole();
		return i > 0;
	}
}
