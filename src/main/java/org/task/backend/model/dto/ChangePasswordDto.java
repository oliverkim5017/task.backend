package org.task.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-14
 */
@Data
public class ChangePasswordDto {

	@NotBlank(message = "请输入旧密码")
	private String oldPassword;
	@NotBlank(message = "请输入新密码")
	private String newPassword;


}
