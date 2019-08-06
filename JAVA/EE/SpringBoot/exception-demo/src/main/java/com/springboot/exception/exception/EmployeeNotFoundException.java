package com.springboot.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 当异常处理程序没有将任何视图页面作为响应返回或者没有对这个异常处理的时候，
 * 将HttpStatus.NOT_FOUND-404 视为默认的http状态代码.
 *
 * 将@ResponseStatus注释与异常类一起使用来定义默认的HTTP状态码
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Employee Not Found") //404
public class EmployeeNotFoundException extends Exception {

	private static final long serialVersionUID = -3332292346834265371L;

	public EmployeeNotFoundException(int code){
		super("EmployeeNotFoundException with code=" + code);
	}

}
