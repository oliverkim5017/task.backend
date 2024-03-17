package org.task.backend.service;

import org.task.backend.model.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 18200
* @description 针对表【comment】的数据库操作Service
* @createDate 2024-03-17 18:57:22
*/
public interface CommentService extends IService<Comment> {

	List<Comment> getComments(int taskId);

	Comment addComment(int taskId, String content, int userId);

}
