package com.journaldev.spring.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
这是我们的全局异常处理程序控制器类。 请注意，该类使用@ControllerAdvice批注进行批注。 
方法也使用@ExceptionHandler注释进行注释。
*/

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	// 请注意，对于SQLException，我将database_error.jsp作为响应页面返回，其http状态代码为200。
	@ExceptionHandler(SQLException.class)
	public String handleSQLException(HttpServletRequest request, Exception ex){
		logger.info("SQLException Occured:: URL="+request.getRequestURL());
		return "database_error";
	}
	
	// 对于IOException，我们返回void，状态代码为404，因此在这种情况下将使用我们的错误页面。
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(IOException.class)
	public void handleIOException(){
		logger.error("IOException handler executed");
		//returning 404 error code
	}
	
	// 正如您所看到的，我在这里没有处理任何其他类型的异常，
	// 我已经在HandlerExceptionResolver实现了这一部分。
}
