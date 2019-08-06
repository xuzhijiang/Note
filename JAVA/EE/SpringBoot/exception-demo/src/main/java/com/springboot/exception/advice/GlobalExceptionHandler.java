package com.springboot.exception.advice;

import com.springboot.exception.controller.EmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 如果定义了多个异常处理程序方法，则使用最接近Exception类的处理程序方法。例如，如果我们为IOException和Exception定义了两个处理程序方法，并且我们的请求处理程序方法抛出IOException，那么将执行IOException的处理程序方法
 *
 * GlobalExceptionHandler中的处理程序方法与EmployeeController的异常处理程序方法相同，在控制器类无法处理异常时使用。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    // 对于SQLException，我将database_error.jsp作为响应页面返回，其http状态代码为200。
    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex){
        return "database_error";
    }

    // 对于IOException，我们返回void，使用注解@ResponseStatue指定了http状态代码为404
    @ExceptionHandler(IOException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="IOException occured")
    public void handleIOException(){
        logger.info("IOException happened!!");
        //returning 404 error code
    }
}
