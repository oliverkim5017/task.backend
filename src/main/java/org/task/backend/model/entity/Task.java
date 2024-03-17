package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-16
 */
@Data
public class Task {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String content;
	private Integer teamId;
	@TableField(exist = false)
	private Team team;
	private Integer stateId;
	@TableField(exist = false)
	private TaskState state;
	private Integer creatorId;
	@TableField(exist = false)
	private User creator;
	@TableField(exist = false)
	private List<TaskNode> nodes;

	public Task() {
		this.nodes = new ArrayList<>();
	}
}
