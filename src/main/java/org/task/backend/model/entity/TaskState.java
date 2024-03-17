package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-16
 */
@Data
public class TaskState {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@TableField("name")
	private String name;

}
