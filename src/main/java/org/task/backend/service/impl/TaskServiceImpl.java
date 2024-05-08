package org.task.backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.task.backend.mapper.TaskMapper;
import org.task.backend.model.dto.ChartDto;
import org.task.backend.model.entity.Task;
import org.task.backend.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * @author 18200
 * @description 针对表【task】的数据库操作Service实现
 * @createDate 2024-03-16 17:59:13
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

}




