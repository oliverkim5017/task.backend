package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.task.backend.annotation.Permission;
import org.task.backend.model.dto.TeamDto;
import org.task.backend.model.entity.Team;
import org.task.backend.model.entity.User;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.TeamService;
import org.task.backend.service.UserService;

import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-13
 */
@RestController
public class TeamController {

	@Resource
	private TeamService teamService;
	@Resource
	private UserService userService;

	@GetMapping("/getTeams")
	public Result getTeams() {
		return Result.success(teamService.list().stream().sorted((a, b) -> (a.getId() - b.getId())).toArray());
	}

	@Permission("管理员")
	@PostMapping("/addTeam")
	public Result addTeam(@RequestBody @Validated TeamDto teamDto) {
		Team team = new Team();
		team.setName(teamDto.getName());
		boolean saved = teamService.save(team);
		return saved? Result.success(team): Result.saveFailed();
	}

	@Permission({"管理员","组长"})
	@PutMapping("/updateTeam/{id}")
	public Result updateTeam(@RequestBody @Validated TeamDto teamDto, @PathVariable int id) {
		Team team = new Team();
		team.setName(teamDto.getName());
		team.setId(id);
		boolean updated = teamService.updateById(team);
		return updated? Result.success(team): Result.updateFailed();
	}

	@Permission("管理员")
	@DeleteMapping("/delTeam/{id}")
	public Result delTeam(@PathVariable int id) {
		List<User> users = userService.list(new QueryWrapper<User>().lambda()
				.eq(User::getTeamId, id));
		if (users != null) {
			return Result.error("删除失败: 已分配组员");
		} else {
			boolean removed = teamService.removeById(id);
			return removed? Result.success(): Result.deleteFailed();
		}
	}

}
