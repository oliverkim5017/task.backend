package org.task.backend.model.dto;

import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-09
 */
@Data
public class ReplyDto {

	private int taskId;
	private String reply;
	private Integer stateId;


}
