package com.springboot.exception.controller;

import com.springboot.exception.domain.Employee;
import com.springboot.exception.domain.ExceptionJSONInfo;
import com.springboot.exception.exception.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 我只在控制器中处理EmployeeNotFoundException，
 */
@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@RequestMapping(value="/emp/{id}", method=RequestMethod.GET)
	public String getEmployee(@PathVariable("id") int id, Model model) throws Exception{
		switch (id) {
			case 1:
				throw new EmployeeNotFoundException(id);
			case 2:
				throw new SQLException("SQLException, id="+id);
			case 3:
				throw new IOException("IOException, id="+id);
			case 4:
				throw new IllegalArgumentException("Illegal Argument exception...");
			case 10:
				Employee emp = new Employee();
				emp.setName("Pankaj");
				emp.setId(id);
				model.addAttribute("employee", emp);
				return "home";
			default:
				//simpleMappingExceptionResolver used for response view
				throw new Exception("Generic Exception, id="+id);
		}
	}

	// 返回JSON响应,状态吗为200
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ExceptionJSONInfo handleIllegalArgumentException(HttpServletRequest request, Exception ex){
		ExceptionJSONInfo response = new ExceptionJSONInfo();
		response.setUrl(request.getRequestURL().toString());
		response.setMessage(ex.getMessage());
		return response;
	}

	// 对于EmployeeNotFoundException，我返回ModelAndView，
	// 因此http状态代码将被发送为OK(200）。
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ModelAndView handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){
		logger.error("Requested URL="+request.getRequestURL());
		logger.error("Exception Raised="+ex);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("url", request.getRequestURL());

		modelAndView.setViewName("employee_not_found_exception");
		return modelAndView;
	}
}
