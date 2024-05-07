package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-16
 */
@Data
@NoArgsConstructor
public class Task {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String content;
	private Integer userId;
	@TableField(exist = false)
	private User user;
	private Integer approveUserId;
	@TableField(exist = false)
	private User approveUser;
	private Integer projectId;
	@TableField(exist = false)
	private Project project;
	private Integer parentId;
	private Integer creatorId;
	@TableField(exist = false)
	private User creator;
	private LocalDateTime createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime endTime;

}
