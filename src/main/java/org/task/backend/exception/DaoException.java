package org.task.backend.exception;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
public class DaoException extends RuntimeException {

	private  Integer code;

	private  String description;

	public DaoException() {

	}

	public DaoException(String message, Integer code, String description) {
		super(message);
		this.code = code;
		this.description = description;
	}

	public DaoException(String message ) {
		super(message);
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}


}
