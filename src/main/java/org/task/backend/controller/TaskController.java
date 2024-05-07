package org.task.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.task.backend.annotation.Permission;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.exception.UnauthorizedException;
import org.task.backend.model.dto.NodeDto;
import org.task.backend.model.dto.TaskDto;
import org.task.backend.model.entity.LoginUser;
import org.task.backend.model.entity.Task;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.TaskService;
import org.task.backend.util.DateRangeParser;
import org.task.backend.util.JwtUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-16
 */
@RestController
public class TaskController {

	@Resource
	private TaskService taskService;


}
