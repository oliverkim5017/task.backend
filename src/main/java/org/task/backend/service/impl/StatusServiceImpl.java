package org.task.backend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.task.backend.mapper.StatusMapper;
import org.task.backend.model.entity.Status;
import org.task.backend.service.StatusService;

/**
* @author 18200
* @description 针对表【status】的数据库操作Service实现
* @createDate 2024-05-06 22:35:08
*/
@Service
public class StatusServiceImpl extends ServiceImpl<StatusMapper, Status>
    implements StatusService {

	@Resource
	private StatusMapper statusMapper;

	@Override
	public boolean resetDefaultStatus(boolean forTask) {
		return statusMapper.resetDefaultStatus(forTask) > 0;
	}
}




