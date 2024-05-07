package org.task.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Data
public class RegisterDto {

	@NotBlank(message = "请输入用户名")
	private String username;
	@NotBlank(message = "请输入密码")
	private String password;
	@NotBlank(message = "请输入姓名")
	private String name;
	@Positive(message = "请选择部门")
	private int departmentId;

}
