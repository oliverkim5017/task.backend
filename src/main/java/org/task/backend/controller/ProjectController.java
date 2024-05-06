package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.task.backend.mapper.ProjectMapper;
import org.task.backend.model.entity.DateRange;
import org.task.backend.model.entity.Project;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.ProjectService;
import org.task.backend.util.DateRangeParser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-06
 */
@RestController
public class ProjectController {

	@Resource
	private ProjectService projectService;

	@GetMapping("/getProjects")
	public Result getProjects(String projectName, String startTime, String endTime, String createTime, String updateTime) {

		DateRange start = new DateRange();
		DateRange end = new DateRange();
		DateRange create = new DateRange();
		DateRange update = new DateRange();

		LocalDateTime[] localDateTimes = DateRangeParser.parseDateRange(startTime);
		if (localDateTimes.length > 0) {
			start.setStartDate(localDateTimes[0].toLocalDate());
			start.setEndDate(localDateTimes[1].toLocalDate());
		}

		localDateTimes = DateRangeParser.parseDateRange(endTime);
		if (localDateTimes.length > 0) {
			end.setStartDate(localDateTimes[0].toLocalDate());
			end.setEndDate(localDateTimes[1].toLocalDate());
		}

		localDateTimes = DateRangeParser.parseDateRange(createTime);
		if (localDateTimes.length > 0) {
			create.setStartDate(localDateTimes[0].toLocalDate());
			create.setEndDate(localDateTimes[1].toLocalDate());
		}
		if (localDateTimes.length > 0) {
			update.setStartDate(localDateTimes[0].toLocalDate());
			update.setEndDate(localDateTimes[1].toLocalDate());
		}

		QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
		LambdaQueryWrapper<Project> lambdaQuery = queryWrapper.lambda().like(Project::getName, projectName);

		if (start.getStartDate() != null && start.getEndDate() != null) {
			lambdaQuery.and(wrapper ->
					wrapper.between(Project::getStartTime, start.getStartDate(), start.getEndDate())
			);
		}
		if (end.getStartDate() != null && end.getEndDate() != null) {
			lambdaQuery.and(wrapper ->
					wrapper.between(Project::getEndTime, end.getStartDate(), end.getEndDate())
			);
		}
		if (create.getStartDate() != null && create.getEndDate() != null) {
			lambdaQuery.and(wrapper ->
					wrapper.between(Project::getCreateTime, create.getStartDate(), create.getEndDate())
			);
		}
		if (update.getStartDate() != null && update.getEndDate() != null){
			lambdaQuery.and(wrapper ->
					wrapper.between(Project::getUpdateTime, update.getStartDate(), update.getEndDate())
			);
		}

		List<Project> projects = projectService.list(lambdaQuery);
		return Result.success(projects);

	}



}
