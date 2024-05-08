package org.task.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.task.backend.model.entity.OperationLog;

import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-08
 */
public interface OperationLogMapper extends BaseMapper<OperationLog> {

	List<OperationLog> getLogs();

}
