package org.task.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.DepartmentService;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@RestController
public class DepartmentController {

	@Resource
	private DepartmentService departmentService;

	@GetMapping("/getDepartments")
	public Result getDepartments() {
		return Result.success(departmentService.list());
	}


}
