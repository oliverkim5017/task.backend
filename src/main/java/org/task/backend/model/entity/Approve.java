package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableField(exist = false)
    private String taskState;
    private Integer startUserId;
    @TableField(exist = false)
    private String startUserName;
    private Integer approveUserId;
    @TableField(exist = false)
    private String approveUserName;
    private String remarks;
    private String reply;
    private boolean forFinish;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approveTime;
}
