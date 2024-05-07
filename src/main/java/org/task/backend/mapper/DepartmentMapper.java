package org.task.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.task.backend.model.entity.Department;

/**
* @author 18200
* @description 针对表【department】的数据库操作Mapper
* @createDate 2024-05-06 21:19:10
* @Entity backend.model/entity.Department
*/
public interface DepartmentMapper extends BaseMapper<Department> {

	int removeDeptById(int id);

}




