package org.task.backend.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.task.backend.model.entity.Status;

/**
* @author 18200
* @description 针对表【status】的数据库操作Service
* @createDate 2024-05-06 22:35:08
*/
public interface StatusService extends IService<Status> {

	boolean resetDefaultStatus(boolean forTask);

}
