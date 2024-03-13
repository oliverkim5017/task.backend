package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.task.backend.dao.DepartmentMapper;
import org.task.backend.model.entity.Department;
import org.task.backend.service.DepartmentService;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
