package org.task.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.task.backend.model.entity.OperationLog;

import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-08
 */
public interface OperationLogService extends IService<OperationLog> {

	List<OperationLog> getLogs();

}
