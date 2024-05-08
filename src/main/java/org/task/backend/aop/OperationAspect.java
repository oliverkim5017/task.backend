package org.task.backend.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.task.backend.annotation.Operation;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.exception.UnauthorizedException;
import org.task.backend.model.entity.OperationLog;
import org.task.backend.model.vo.result.Result;
import org.task.backend.service.OperationLogService;

import java.util.Arrays;
import java.util.Random;

/**
 * @author OliverKim
 * @description
 * @since 2024-05-08
 */
@Aspect
@Component
public class OperationAspect {

	private final ObjectMapper objectMapper = new ObjectMapper();

	{
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Pointcut("@annotation(org.task.backend.annotation.Operation)")
	private void permissionCheck() {
	}

	@Resource
	private OperationLogService operationLogService;

	@Around("@annotation(operation)")
	public Object logOperation(ProceedingJoinPoint joinPoint, Operation operation) throws Throwable {

		String operationName = operation.value();
		OperationLog operationLog = new OperationLog();
		operationLog.setName(operationName);
		Integer userId = LoginThreadLocal.getUserId();
		if (userId == null) {
			throw new RuntimeException("未登录");
		}
		operationLog.setUserId(userId);


		String params = objectMapper.writeValueAsString(joinPoint.getArgs());
		operationLog.setParams(params);

		Object result = null;
		try {
			result = joinPoint.proceed();
			if (result instanceof Result) {
				HttpStatusCode statusCode = ((Result) result).getStatusCode();
				operationLog.setCode(statusCode.value());
			}
			RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
			if (attrs instanceof ServletRequestAttributes) {
				HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();

				// 正确获取客户端的 IP 地址
				String clientIP = request.getRemoteAddr();
				operationLog.setIp(clientIP);
			}
		} catch (Exception e) {
			operationLog.setCode(500);
			throw e;
		} finally {
			operationLogService.save(operationLog);
		}

		return result;
	}
}
