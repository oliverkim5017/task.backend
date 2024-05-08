package org.task.backend.model.dto;

import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-08
 */
@Data
public class ApproveDto {


	private Integer id;
	private Integer taskId;
	private String remarks;
	private boolean forFinish;

}
