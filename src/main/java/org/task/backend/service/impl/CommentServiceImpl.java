package org.task.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.task.backend.model.entity.Comment;
import org.task.backend.model.entity.User;
import org.task.backend.service.CommentService;
import org.task.backend.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.task.backend.service.UserService;

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
	public List<Comment> getComments(int taskId) {
		List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().lambda()
				.eq(Comment::getTaskId, taskId));
		Map<Integer, String> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, User::getName));
		comments.forEach(c -> {
			User user = new User();
			user.setId(c.getUserId());
			user.setName(userMap.get(c.getUserId()));
			c.setUser(user);
		});
		return comments;
	}

	@Override
	public Comment addComment(int taskId, String content, int userId) {
		Comment comment = new Comment();
		comment.setTaskId(taskId);
		comment.setContent(content);
		comment.setUserId(userId);
		commentMapper.insert(comment);
		User user = userService.getById(userId);
		comment.setUser(user);
		return comment;
	}
}




