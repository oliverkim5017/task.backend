package org.task.backend.model.vo.result;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * @author OliverKim
 * @description
 * @since 2023-10-25
 */
public class Result extends ResponseEntity<R> {

	public Result(R body, HttpStatusCode status) {
		super(body, status);
	}

	public static Result success(){
		return new Result(R.success(), HttpStatus.OK);
	}

	public static Result success(String msg){
		return new Result(R.success(msg), HttpStatus.OK);
	}

	public static Result success(Object data){
		return new Result(R.success(data), HttpStatus.OK);
	}

	public static Result success(String msg, Object data){
		return new Result(R.success(msg, data), HttpStatus.OK);
	}

	public static Result error(String msg) {
		return new Result(R.error(msg), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static Result updateFailed(){
		return new Result(R.updateFailed(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static Result deleteFailed(){
		return new Result(R.deleteFailed(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static Result saveFailed(){
		return new Result(R.saveFailed(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static Result badRequestArgs(){
		return new Result(R.badRequest(), HttpStatus.BAD_REQUEST);
	}

	public static Result badRequestArgs(String msg){
		return new Result(R.badRequest(msg), HttpStatus.BAD_REQUEST);
	}

	public static Result notFoundResource(){
		return new Result(R.notFound(), HttpStatus.NOT_FOUND);
	}

}
