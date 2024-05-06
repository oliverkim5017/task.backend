package org.task.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.task.backend.model.entity.Role;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
public interface RoleService extends IService<Role> {
	boolean resetDefaultRole();

}
