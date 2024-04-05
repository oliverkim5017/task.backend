package org.task.backend.mapper;

import org.task.backend.model.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 18200
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2024-03-17 18:57:22
* @Entity org.task.backend.model.entity.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {


	List<Comment> getComments(Integer taskId);

	List<Comment> getMyComments(Integer userId);

}




