package org.task.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.task.backend.model.entity.Task;

import java.time.LocalDate;
import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-17
 */
public interface TaskMapper extends BaseMapper<Task> {
	Task getTaskById(Integer id);

	List<Task> getTasks(Integer teamId, Integer stateId, LocalDate startTime, LocalDate endTime);

}
