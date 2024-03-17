package org.task.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-16
 */
@Data
public class TeamDto {

	@NotBlank
	private String name;

}
