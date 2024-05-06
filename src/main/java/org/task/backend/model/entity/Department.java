package org.task.backend.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName department
 */
@TableName(value ="department")
@Data
public class Department implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer parentId;

}
