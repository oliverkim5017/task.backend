package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.task.backend.mapper.OperationLogMapper;
import org.task.backend.model.entity.OperationLog;
import org.task.backend.service.OperationLogService;

import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-08
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
	implements OperationLogService {

	@Resource
	private OperationLogMapper operationLogMapper;

	@Override
	public List<OperationLog> getLogs() {
		return operationLogMapper.getLogs();
	}
}
