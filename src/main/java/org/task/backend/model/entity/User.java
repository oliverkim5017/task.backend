package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Data
public class User {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@TableField("username")
	private String username;
	@TableField("password")
	private String password;
	@TableField("name")
	private String name;
	@TableField("departmentId")
	private Integer departmentId;
	@TableField(exist = false)
	private Department department;
	@TableField("roleId")
	private Integer roleId;
	@TableField(exist = false)
	private Role role;

}
