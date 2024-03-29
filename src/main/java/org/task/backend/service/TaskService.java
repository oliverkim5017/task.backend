package org.task.backend.service;

import org.task.backend.model.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
* @author 18200
* @description 针对表【task】的数据库操作Service
* @createDate 2024-03-16 17:59:13
*/
public interface TaskService extends IService<Task> {

	Task getTaskById(Integer id);

	List<Task> getTasks(List<Integer> teamIds, List<Integer>  stateIds, LocalDate startTime, LocalDate endTime);

}
