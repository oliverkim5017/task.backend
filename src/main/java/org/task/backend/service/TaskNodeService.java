package org.task.backend.service;

import org.task.backend.model.entity.TaskNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 18200
* @description 针对表【task_node】的数据库操作Service
* @createDate 2024-03-17 16:16:54
*/
public interface TaskNodeService extends IService<TaskNode> {

	List<TaskNode> getTaskNodes(Integer userId);

}
