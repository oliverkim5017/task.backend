package org.task.backend.model.entity;

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

    private Date createTime;

    private Date updateTime;

    private Integer createdBy;

    private Integer updatedBy;

}
