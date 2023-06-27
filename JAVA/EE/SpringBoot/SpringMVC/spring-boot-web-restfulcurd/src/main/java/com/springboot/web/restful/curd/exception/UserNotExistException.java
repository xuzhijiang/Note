package com.springboot.web.restful.curd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 将@ResponseStatus注释与异常类一起使用来定义默认的HTTP状态码
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason=" User Not Exist")
public class UserNotExistException extends RuntimeException{
    public UserNotExistException() { super("用户不存在"); }
}
