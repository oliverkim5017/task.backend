package org.task.backend.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.task.backend.model.entity.Department;

/**
* @author 18200
* @description 针对表【department】的数据库操作Service
* @createDate 2024-05-06 21:19:10
*/
public interface DepartmentService extends IService<Department> {


	Department getDepartmentById(int id);

}
