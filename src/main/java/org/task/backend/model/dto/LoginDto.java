package org.task.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Data
public class LoginDto {

	@NotBlank(message = "请输入用户名")
	private String username;
	@NotBlank(message = "请输入密码")
	private String password;

}
