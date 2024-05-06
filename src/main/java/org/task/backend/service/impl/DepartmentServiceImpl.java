package org.task.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.task.backend.mapper.DepartmentMapper;
import org.task.backend.model.entity.Department;
import org.task.backend.service.DepartmentService;

/**
* @author 18200
* @description 针对表【department】的数据库操作Service实现
* @createDate 2024-05-06 21:19:10
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService {

	@Resource
	private DepartmentMapper departmentMapper;

	@Override
	public Department getDepartmentById(int id) {
		return departmentMapper.selectById(id);
	}
}




