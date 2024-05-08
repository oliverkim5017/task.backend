package org.task.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.task.backend.mapper.ApproveMapper;
import org.task.backend.model.entity.Approve;
import org.task.backend.service.ApproveService;

import java.util.List;

/**
* @author 18200
* @description 针对表【approve】的数据库操作Service实现
* @createDate 2024-05-08 23:02:56
*/
@Service
public class ApproveServiceImpl extends ServiceImpl<ApproveMapper, Approve>
    implements ApproveService {


	@Resource
	private ApproveMapper approveMapper;

	@Override
	public List<Approve> getToApprove(Integer userId) {
		return approveMapper.approveMapper(userId);
	}
}




