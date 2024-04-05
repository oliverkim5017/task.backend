package org.task.backend.model.dto;

import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-04-05
 */
@Data
public class UserSelectDto {

	private int id;
	private String name;
	private int teamId;
}
