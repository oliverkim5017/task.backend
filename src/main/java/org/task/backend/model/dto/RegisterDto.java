package org.task.backend.model.dto;

import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Data
public class RegisterDto {

	private String username;
	private String password;
	private String name;
	private int departmentId;

}
