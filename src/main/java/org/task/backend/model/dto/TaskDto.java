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
public class TaskDto {

	private Integer id;
	@NotBlank(message = "请输入任务内容")
	private String content;
	@Positive(message = "请选择正确的组")
	private int teamId;
	@Positive(message = "请选择起始状态")
	private int stateId;
	private LocalDate deadLine;

}
