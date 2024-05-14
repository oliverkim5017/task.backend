package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.model.dto.ApproveDto;
import org.task.backend.model.dto.ReplyDto;
import org.task.backend.model.entity.Approve;
import org.task.backend.model.entity.Task;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.ApproveService;
import org.task.backend.service.TaskService;

import java.util.List;


/**
 * @author OliverKim
 * @description
 * @since 2024-05-08
 */
@RestController
public class ApproveController {

	@Resource
	private ApproveService approveService;
	@Resource
	private TaskService taskService;


	@PostMapping("/startApproval")
	public Result startApprove(@RequestBody ApproveDto approveDto) {
		Approve approve = new Approve();
		approve.setId(approveDto.getId());
		approve.setTaskId(approveDto.getTaskId());
		Task task = taskService.getById(approveDto.getTaskId());
		if (task == null || task.getApproveUserId() == null) {
			return Result.error("项目未设置审核人");
		}
		approve.setApproveUserId(task.getApproveUserId());
		approve.setRemarks(approveDto.getRemarks());
		Integer userId = LoginThreadLocal.getUserId();
		approve.setStartUserId(userId);
		approve.setForFinish(approveDto.isForFinish());
		boolean save;
		if (approve.getId() != null) {
			save = approveService.updateById(approve);
		} else {
			save = approveService.save(approve);
		}
		return save ? Result.success() : Result.saveFailed();
	}

	@GetMapping("/getApproval")
	public Result getApproval(Integer taskId, Integer userId) {
		Approve approve = approveService.getOne(new QueryWrapper<Approve>().lambda()
				.eq(Approve::getTaskId, taskId)
				.eq(Approve::getStartUserId, userId)
				.isNull(Approve::getReply));
		return Result.success(approve);
	}

	@GetMapping("/getToApprove")
	public Result getToApprove() {
		Integer userId = LoginThreadLocal.getUserId();
		List<Approve> list =  approveService.getToApprove(userId);
		return Result.success(list);
	}

	@PostMapping("/approveReply")
	public Result approveReply(@RequestBody ReplyDto replyDto) {
		Approve approve = approveService.getOne(new QueryWrapper<Approve>().lambda()
				.eq(Approve::getTaskId, replyDto.getTaskId())
				.isNull(Approve::getReply));
		if (approve == null) {
			return Result.error("审批不存在");
		}
		approve.setReply(replyDto.getReply());
		boolean b = approveService.updateById(approve);
		if (!b) {
			return Result.saveFailed();
		}
		Task task = taskService.getById(replyDto.getTaskId());
		if (task == null) {
			return Result.error("任务不存在");
		}
		if (replyDto.getStateId() != null) {
			task.setStateId(replyDto.getStateId());
		}
		boolean b1 = taskService.updateById(task);
		return b1 ? Result.success() : Result.saveFailed();
	}


}
