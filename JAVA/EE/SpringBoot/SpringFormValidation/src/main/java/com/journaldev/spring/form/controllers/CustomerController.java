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


// 我们有两个控制器类，
// 一个用于基于注释的表单验证(CustomerController)，
// 另一个用于我们的自定义验证器(EmployeeController)。


// 我们可以通过两种方式创建自定义验证器实现 -
// 第一种是创建一个注释，以确认JSR-303规范并实现它的Validator类。
// 第二种方法是实现org.springframework.validation.Validator接口，
// 并使用@InitBinder注释将其设置为Controller类中的验证器。


// 我们将使用它的实现工件hibernate-validator来使用JSR-303规范。
// 我们将使用基于注释的表单验证，并基于JSR-303规范标准创建我们自己的自定义验证器。
// 我们还将通过实现Validator接口创建自己的自定义验证器类，
// 并在其中一个控制器处理程序方法中使用它。
@Controller
public class CustomerController {

	// 当我们使用基于注释的表单验证时，
	// 我们只需要在我们的控制器处理程序方法实现中进行少量更改以使其正常工作。
	private static final Logger logger = LoggerFactory
			.getLogger(CustomerController.class);
	
	private Map<String, Customer> customers = null;
	
	public CustomerController(){
		customers = new HashMap<String, Customer>();
	}

	//另一个要注意的重点是我们在模型中添加“customer”属性，
// 这是让Spring框架知道在表单页面中使用哪个模型对象所必需的。
// 如果我们不这样做，对象绑定到表单数据将不会发生，我们的表单验证将不起作用。
	@RequestMapping(value = "/cust/save", method = RequestMethod.GET)
	public String saveCustomerPage(Model model) {
		logger.info("Returning custSave.jsp page");
		model.addAttribute("customer", new Customer());
		return "custSave";
	}

	//首先，我们需要使用@Valid注释注解我们想要验证的模型对象(Customer)。
// 然后我们需要在方法中使用BindingResult参数，spring负责用错误消息填充它。
// 处理程序方法逻辑非常简单，如果有任何错误，那么我们就用同一页面响应，
// 否则我们将用户重定向到成功页面。
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
