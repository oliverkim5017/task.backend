package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @TableName project
 */
@TableName(value ="project")
@Data
public class Project implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String detail;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endTime;
    private int statusId;

    private int departmentId;
    @TableField(exist = false)
    private Department department;
    @TableField(exist = false)
    private User user;
    private int userId;
    @TableField(exist = false)
    private User approveUser;
    private int approveUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer createdBy;
    @TableField(exist = false)
    private User creator;
    private Integer updatedBy;
    @TableField(exist = false)
    private User updater;


}
