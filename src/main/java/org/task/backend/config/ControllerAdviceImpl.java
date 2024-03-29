package org.task.backend.config;

import com.alibaba.druid.pool.GetConnectionTimeoutException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.task.backend.exception.DaoException;
import org.task.backend.model.vo.result.Result;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdviceImpl {

	@ExceptionHandler(ExpiredJwtException.class)
	public Result handleExpiredJwtException(ExpiredJwtException e){
		log.error(e.getMessage());
		e.printStackTrace();
		return Result.error("登录信息已过期");
	}

	@ExceptionHandler(GetConnectionTimeoutException.class)
	public Result handleGetConnectionTimeoutException(GetConnectionTimeoutException e){
		log.error(e.getMessage());
		e.printStackTrace();
		return Result.error("数据库连接超时");
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public Result handleException(MethodArgumentNotValidException e){
		StringBuilder msg = new StringBuilder();
		for (ObjectError error : e.getAllErrors()) {
			msg.append(error.getDefaultMessage()).append(";");
		}
		log.error(e.getMessage());
		e.printStackTrace();
		return Result.badRequestArgs(msg.toString());
	}

	@ExceptionHandler(DaoException.class)
	public Result handleDaoException(DaoException e){
		log.error(e.getMessage());
		e.printStackTrace();
		return Result.error(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public Result handleExceptions(Exception e){
		log.error(e.getMessage());
		e.printStackTrace();
		return Result.error(e.getMessage());
	}

}
