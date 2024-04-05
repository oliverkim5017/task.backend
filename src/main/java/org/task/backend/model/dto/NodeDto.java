package org.task.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-17
 */
@Data
public class NodeDto {

	private Integer id;
	@Positive(message = "没有指定主任务")
	private int parentTaskId;
	@NotBlank(message = "任务内容不能为空")
	private String content;
	@Positive(message = "没有指定任务成员")
	private int userId;
	private Integer stateId;
	private LocalDate deadLine;


}
