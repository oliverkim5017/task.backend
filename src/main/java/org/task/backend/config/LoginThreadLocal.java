package org.task.backend.config;


import io.jsonwebtoken.Claims;
import org.task.backend.exception.UnauthorizedException;
import org.task.backend.model.entity.LoginUser;
import org.task.backend.util.JwtUtil;

import java.util.Optional;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
public class LoginThreadLocal {

	private static final ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

	public static LoginUser get(){
		return threadLocal.get();
	}

	public static void  set(LoginUser LoginUser){
		threadLocal.set(LoginUser);
	}

	public static void destroy(){
		threadLocal.remove();
	}

	public static Integer getUserId() {
		LoginUser loginUser = threadLocal.get();
		String token = loginUser.getToken();
		Optional.ofNullable(token).orElseThrow(UnauthorizedException::new);
		Claims claims = JwtUtil.getClaimsFromToken(token);
		return claims.get("userId", Integer.class);
	}

}
