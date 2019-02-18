package com.journaldev.spring.form.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.form.model.Employee;

// 我们有两个控制器类，一个用于基于注释的表单验证，另一个用于我们的自定义验证器。

// 当我们使用基于注释的表单验证时，我们只需要在我们的控制器处理程序方法实现中进行少量更改以使其正常工作。

//首先，我们需要注释我们想要使用@Valid注释验证的模型对象。 然后我们需要在方法中使用BindingResult参数，spring负责用错误消息填充它。 处理程序方法逻辑非常简单，如果有任何错误我们正在响应同一页面，否则我们将用户重定向到成功页面。
//
//另一个要注意的重点是我们在模型中添加“customer”属性，这是让Spring框架知道在表单页面中使用哪个模型对象所必需的。 如果我们不这样做，对象绑定到表单数据将不会发生，我们的表单验证将不起作用。

// Spring MVC Framework默认支持JSR-303规范，我们只需要在Spring MVC应用程序中
// 添加JSR-303及其实现依赖项。 Spring还提供了@Validator注释和BindingResult类，
// 通过它我们可以在控制器请求处理程序方法中获取由Validator实现引发的错误。
@Controller
public class EmployeeController {

	private static final Logger logger = LoggerFactory
			.getLogger(EmployeeController.class);

	private Map<Integer, Employee> emps = null;

	@Autowired
	@Qualifier("employeeValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	public EmployeeController() {
		emps = new HashMap<Integer, Employee>();
	}

	@ModelAttribute("employee")
	public Employee createEmployeeModel() {
		// ModelAttribute value should be same as used in the empSave.jsp
		return new Employee();
	}

	@RequestMapping(value = "/emp/save", method = RequestMethod.GET)
	public String saveEmployeePage(Model model) {
		logger.info("Returning empSave.jsp page");
		return "empSave";
	}

	@RequestMapping(value = "/emp/save.do", method = RequestMethod.POST)
	public String saveEmployeeAction(
			@ModelAttribute("employee") @Validated Employee employee,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			logger.info("Returning empSave.jsp page");
			return "empSave";
		}
		logger.info("Returning empSaveSuccess.jsp page");
		model.addAttribute("emp", employee);
		emps.put(employee.getId(), employee);
		return "empSaveSuccess";
	}
}
