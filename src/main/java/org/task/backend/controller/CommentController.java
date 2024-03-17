package org.task.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.model.entity.Comment;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.CommentService;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-17
 */
@RestController
public class CommentController {

	@Resource
	private CommentService commentService;

	@GetMapping("/getComments/{taskId}")
	public Result getComments(@PathVariable int taskId) {
		return Result.success("success", commentService.getComments(taskId));
	}

	@PostMapping("/addComment/{taskId}")
	public Result addComment(@PathVariable int taskId, String content) {
		Integer userId = LoginThreadLocal.getUserId();
		if (userId == null) {
			return Result.error("请先登录");
		}
		Comment comment = commentService.addComment(taskId, content, userId);
		return Result.success("success", comment);
	}

	@DeleteMapping("/delComment/{id}")
	public Result delComment(@PathVariable int id) {
		boolean removed = commentService.removeById(id);
		return removed? Result.success("success"): Result.deleteFailed();
	}

}
