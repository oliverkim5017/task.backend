package org.task.backend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.task.backend.model.entity.LoginUser;

/**
 * @author OliverKim
 * @description
 * @since 2024-01-11
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String authorization = request.getHeader("Authorization");
		LoginUser loginUser = new LoginUser();
		loginUser.setToken(authorization);
		LoginThreadLocal.set(loginUser);
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
		LoginThreadLocal.destroy();
		HandlerInterceptor.super.afterCompletion(request, response, handler, e);
	}


}
