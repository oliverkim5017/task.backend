package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.task.backend.model.entity.Project;
import org.task.backend.service.ProjectService;
import org.task.backend.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

/**
* @author 18200
* @description 针对表【project】的数据库操作Service实现
* @createDate 2024-05-06 20:18:10
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService{

}




