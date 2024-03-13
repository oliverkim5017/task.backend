package org.me.community.config;

import org.me.community.model.entity.LoginUser;

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

}
