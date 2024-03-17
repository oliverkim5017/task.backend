package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.task.backend.model.entity.TaskNode;
import org.task.backend.service.TaskNodeService;
import org.task.backend.mapper.TaskNodeMapper;
import org.springframework.stereotype.Service;

/**
* @author 18200
* @description 针对表【task_node】的数据库操作Service实现
* @createDate 2024-03-17 16:16:54
*/
@Service
public class TaskNodeServiceImpl extends ServiceImpl<TaskNodeMapper, TaskNode>
    implements TaskNodeService{

}




