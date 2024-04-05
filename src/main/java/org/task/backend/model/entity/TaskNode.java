package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.task.backend.model.dto.NodeDto;

/**
 * @TableName task_node
 */
@TableName(value = "task_node")
@Data
@NoArgsConstructor
public class TaskNode implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer parentTaskId;
	private String content;
	private Integer userId;
	@TableField(exist = false)
	private User assignedUser;
	private Integer stateId;
	@TableField(exist = false)
	private TaskState taskState;
	private int nodeIndex;
	private Integer creatorId;
	@TableField(exist = false)
	private User creator;
	private LocalDate deadLine;

	public TaskNode(NodeDto nodeDto) {
		this.id = nodeDto.getId();
		this.parentTaskId = nodeDto.getParentTaskId();
		this.content = nodeDto.getContent();
		this.userId = nodeDto.getUserId();
		this.stateId = nodeDto.getStateId();
		this.deadLine = nodeDto.getDeadLine();
	}
}