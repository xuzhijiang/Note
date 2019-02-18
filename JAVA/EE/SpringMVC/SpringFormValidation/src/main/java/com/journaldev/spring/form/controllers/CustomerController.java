package com.journaldev.spring.form.controllers;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.journaldev.spring.form.model.Customer;

// 我们可以通过两种方式创建自定义验证器实现 -
// 第一种是创建一个注释，以确认JSR-303规范并实现它的Validator类。
// 第二种方法是实现org.springframework.validation.Validator接口，
// 并使用@InitBinder注释将其设置为Controller类中的验证器。



// 让我们在Spring Tool Suite中创建一个简单的Spring MVC项目，
// 我们将使用它的实现工件hibernate-validator来使用JSR-303规范。
// 我们将使用基于注释的表单验证，并基于JSR-303规范标准创建我们自己的自定义验证器。
// 我们还将通过实现Validator接口创建自己的自定义验证器类，
// 并在其中一个控制器处理程序方法中使用它。我们的最终项目如下图所示。
@Controller
public class CustomerController {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomerController.class);
	
	private Map<String, Customer> customers = null;
	
	public CustomerController(){
		customers = new HashMap<String, Customer>();
	}

	@RequestMapping(value = "/cust/save", method = RequestMethod.GET)
	public String saveCustomerPage(Model model) {
		logger.info("Returning custSave.jsp page");
		model.addAttribute("customer", new Customer());
		return "custSave";
	}

	@RequestMapping(value = "/cust/save.do", method = RequestMethod.POST)
	public String saveCustomerAction(
			@Valid Customer customer,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			logger.info("Returning custSave.jsp page");
			return "custSave";
		}
		logger.info("Returning custSaveSuccess.jsp page");
		model.addAttribute("customer", customer);
		customers.put(customer.getEmail(), customer);
		return "custSaveSuccess";
	}

}
