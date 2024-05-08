package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-08
 */
@TableName("operation_log")
@Data
public class OperationLog {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String name;
	private String params;
	private Integer userId;
	@TableField(exist = false)
	private User user;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time;
	private int code;
	private String ip;

}
