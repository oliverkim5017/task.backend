package org.task.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-04-05
 */
@Data
public class CommentDto {

	@NotBlank(message = "评论不能为空")
	private String content;
	private Integer toUserId;

}
