package org.task.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.task.backend.model.entity.DateRange;
import org.task.backend.model.entity.Project;

import java.util.List;

/**
* @author 18200
* @description 针对表【project】的数据库操作Mapper
* @createDate 2024-05-06 20:18:10
* @Entity generator.domain.Project
*/
public interface ProjectMapper extends BaseMapper<Project> {

	List<Project> getProjects(String projectName, DateRange create, DateRange update);

}




