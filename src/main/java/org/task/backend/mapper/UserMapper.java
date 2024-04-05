package org.task.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.task.backend.model.entity.User;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
public interface UserMapper extends BaseMapper<User> {

	User getMe(Integer id);

	User getById(Integer id);
}
