package org.task.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
@Getter
public class UnauthorizedException extends RuntimeException{
	private final Integer code;
	private final String message;

	public UnauthorizedException() {
		this.code = HttpStatus.UNAUTHORIZED.value();
		this.message = "未登录";
	}
}
