package org.task.backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Integer id;

    private String name;

    private String detail;

    private LocalDate startTime;
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

    private Date createTime;

    private Date updateTime;

    private Integer createdBy;

    private Integer updatedBy;

}
