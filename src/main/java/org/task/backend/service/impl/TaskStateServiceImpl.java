package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.task.backend.model.entity.TaskState;
import org.task.backend.service.TaskStateService;
import org.task.backend.mapper.TaskStateMapper;
import org.springframework.stereotype.Service;

/**
* @author 18200
* @description 针对表【task_state】的数据库操作Service实现
* @createDate 2024-03-17 14:14:20
*/
@Service
public class TaskStateServiceImpl extends ServiceImpl<TaskStateMapper, TaskState>
    implements TaskStateService{

}




