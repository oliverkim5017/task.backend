package org.task.backend.mapper;

import org.task.backend.model.entity.TaskNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 18200
* @description 针对表【task_node】的数据库操作Mapper
* @createDate 2024-03-17 16:16:54
* @Entity org.task.backend.model.entity.TaskNode
*/
public interface TaskNodeMapper extends BaseMapper<TaskNode> {

	List<TaskNode> getTaskNodes(Integer userId);

}




