package org.task.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
@Getter
public class ForbiddenTargetException extends RuntimeException{
	private final Integer code;
	private final String message;

	public ForbiddenTargetException() {
		this.code = HttpStatus.FORBIDDEN.value();
		this.message = "无权限";
	}
}
