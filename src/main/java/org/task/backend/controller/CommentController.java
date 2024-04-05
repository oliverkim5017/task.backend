package org.task.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.model.dto.CommentDto;
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
	public Result getComments(@PathVariable Integer taskId) {
		return Result.success("success", commentService.getComments(taskId));
	}

	@GetMapping("/getMyComments")
	public Result getMyComments() {
		Integer userId = LoginThreadLocal.getUserId();
		return Result.success("success", commentService.getMyComments(userId));
	}

	@PostMapping("/addComment/{taskId}")
	public Result addComment(@PathVariable int taskId, @RequestBody @Validated CommentDto commentDto) {
		Integer userId = LoginThreadLocal.getUserId();
		Boolean b = commentService.addComment(taskId, commentDto, userId);
		if (b) {
			return Result.success("success");
		}
		return Result.saveFailed();
	}

	@DeleteMapping("/delComment/{id}")
	public Result delComment(@PathVariable int id) {
		boolean removed = commentService.removeById(id);
		return removed? Result.success("success"): Result.deleteFailed();
	}

}
