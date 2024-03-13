package org.me.community.config;

import com.alibaba.druid.pool.GetConnectionTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.me.community.exception.DaoException;
import org.me.community.model.vo.result.Result;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdviceImpl {



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
