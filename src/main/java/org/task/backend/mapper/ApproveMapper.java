package org.task.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.task.backend.model.entity.Approve;

import java.util.List;

/**
* @author 18200
* @description 针对表【approve】的数据库操作Mapper
* @createDate 2024-05-08 23:02:56
* @Entity backend.model/entity.Approve
*/
public interface ApproveMapper extends BaseMapper<Approve> {

	List<Approve> approveMapper(Integer userId);

}




