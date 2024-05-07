package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String name;
	private Integer departmentId;
	@TableField(exist = false)
	private Department department;
	private Integer roleId;
	@TableField(exist = false)
	private Role role;
	private boolean disabled;

}
