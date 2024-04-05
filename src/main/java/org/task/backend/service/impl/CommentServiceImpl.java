package org.task.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.task.backend.model.dto.CommentDto;
import org.task.backend.model.entity.Comment;
import org.task.backend.model.entity.User;
import org.task.backend.service.CommentService;
import org.task.backend.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.task.backend.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 18200
 * @description 针对表【comment】的数据库操作Service实现
 * @createDate 2024-03-17 18:57:22
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

	@Resource
	private CommentMapper commentMapper;
	@Resource
	private UserService userService;

	@Override
	public List<Comment> getComments(Integer taskId) {
		return commentMapper.getComments(taskId);
	}

	@Override
	public Boolean addComment(int taskId, CommentDto commentDto, int userId) {
		Comment comment = new Comment();
		comment.setTaskId(taskId);
		comment.setContent(commentDto.getContent());
		comment.setToUserId(commentDto.getToUserId());
		comment.setUserId(userId);
		comment.setCreatedAt(LocalDateTime.now());
		commentMapper.insert(comment);
		return true;
	}

	@Override
	public List<Comment> getMyComments(Integer userId) {
		return commentMapper.getMyComments(userId);
	}
}




