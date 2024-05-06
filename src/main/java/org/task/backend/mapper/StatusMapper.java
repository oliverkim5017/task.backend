package org.task.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.task.backend.model.entity.Status;

/**
* @author 18200
* @description 针对表【status】的数据库操作Mapper
* @createDate 2024-05-06 22:35:08
* @Entity backend.model/entity.Status
*/
public interface StatusMapper extends BaseMapper<Status> {

	int resetDefaultStatus();

}




