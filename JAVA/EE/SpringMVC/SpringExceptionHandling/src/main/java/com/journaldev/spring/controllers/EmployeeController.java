package com.journaldev.spring.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.journaldev.spring.exceptions.EmployeeNotFoundException;
import com.journaldev.spring.model.Employee;

// 让我们看看我们的控制器类，我们将抛出不同类型的异常。

/*
 
	请注意，对于EmployeeNotFoundException处理程序，我返回ModelAndView，
	因此http状态代码将被发送为OK（200）。 如果它将返回void，那么http状态代码将被发送为404.
	我们将在我们的 global exception handler implementation中研究这种类型的实现。
	
	由于我只在控制器中处理EmployeeNotFoundException，
	因此我们的控制器抛出的所有其他异常将由全局异常处理程序处理(global exception handler)。 

*/

@Controller
public class EmployeeController {
	
	// http://localhost:8080/spring/emp/10
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@RequestMapping(value="/emp/{id}", method=RequestMethod.GET)
	public String getEmployee(@PathVariable("id") int id, Model model) throws Exception{
		//deliberately throwing different types of exception
		if(id==1){//controller based exception handler used
			throw new EmployeeNotFoundException(id);
		}else if(id==2){//global exception handler used with view as response
			throw new SQLException("SQLException, id="+id);
		}else if(id==3){//404 error page used
			throw new IOException("IOException, id="+id);
		}else if(id==10){//valid response
			Employee emp = new Employee();
			emp.setName("Pankaj");
			emp.setId(id);
			model.addAttribute("employee", emp);
			return "home";
		}else {//simpleMappingExceptionResolver used for response view
			throw new Exception("Generic Exception, id="+id);
		}
		
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ModelAndView handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){
		logger.error("Requested URL="+request.getRequestURL());
		logger.error("Exception Raised="+ex);
		
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("error");
	    return modelAndView;
	}
	
//	Spring异常处理程序JSON响应
	
//	我将解释如何从异常处理程序方法发送JSON响应。

//	我们的应用程序已经配置了所有JSON依赖项和jsonMessageConverter，我们需要实现异常处理程序方法。

//	为简单起见，我将重写EmployeeController handleEmployeeNotFoundException（）方法以返回JSON响应。

//	只需使用以下代码更新EmployeeController异常处理程序方法，然后再次部署应用程序。
	
	//再次访问: http://localhost:8080/spring/emp/1
	
//	@ExceptionHandler(EmployeeNotFoundException.class)
//	public @ResponseBody ExceptionJSONInfo handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){
//		
//		ExceptionJSONInfo response = new ExceptionJSONInfo();
//		response.setUrl(request.getRequestURL().toString());
//		response.setMessage(ex.getMessage());
//		
//		return response;
//	}
	
}
