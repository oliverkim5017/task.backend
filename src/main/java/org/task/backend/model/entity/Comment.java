package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    @TableField(exist = false)
    private User user;
    private String content;
    private Integer taskId;

    private static final long serialVersionUID = 1L;
}