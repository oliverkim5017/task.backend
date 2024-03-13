package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.task.backend.dao.RoleMapper;
import org.task.backend.model.entity.Role;
import org.task.backend.service.RoleService;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
