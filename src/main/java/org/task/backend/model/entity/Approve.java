package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName approve
 */
@TableName(value ="approve")
@Data
public class Approve implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer taskId;
    @TableField(exist = false)
    private String taskName;
    private Integer startUserId;
    @TableField(exist = false)
    private String startUserName;
    private Integer approveUserId;
    @TableField(exist = false)
    private String approveUserName;
    private String remarks;
    private String reply;
    private boolean forFinish;
}
