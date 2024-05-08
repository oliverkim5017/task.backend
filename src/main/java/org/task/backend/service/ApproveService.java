package org.task.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.task.backend.model.entity.Approve;

import java.util.List;

/**
* @author 18200
* @description 针对表【approve】的数据库操作Service
* @createDate 2024-05-08 23:02:56
*/
public interface ApproveService extends IService<Approve> {

	List<Approve> getToApprove(Integer userId);

}
