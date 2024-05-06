package org.task.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.task.backend.model.entity.Role;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
public interface RoleMapper extends BaseMapper<Role> {


	int resetDefaultRole();


}
