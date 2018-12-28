package com.journaldev.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 我们的应用程序使用的自定义异常类。

/*
 	请注意，我们可以将@ResponseStatus注释与异常类一起使用来定义由我们的应用程序发送的
 	在我们的应用程序抛出此类异常并由异常处理实现处理时将发送的HTTP code。

	正如您所看到的，我将HTTP状态设置为404并且我们为此定义了错误页面，
	因此如果我们不返回任何视图，我们的应用程序应该使用此错误页面来处理此类异常。

	我们还可以覆盖异常处理程序方法中的状态代码，当我们的异常处理程序方法没有将
	任何视图页面作为响应返回时，将其视为默认的http状态代码。
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Employee Not Found") //404
public class EmployeeNotFoundException extends Exception {

	private static final long serialVersionUID = -3332292346834265371L;

	public EmployeeNotFoundException(int id){
		super("EmployeeNotFoundException with id="+id);
	}
}
