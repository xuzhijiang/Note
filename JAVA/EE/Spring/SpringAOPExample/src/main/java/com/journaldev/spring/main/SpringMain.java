package com.journaldev.spring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.service.EmployeeService;

// how all these aspects cut through the bean methods.
// 所有这些aspects如何切入bean方法。

public class SpringMain {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		// 参数1：要检索的bean的名字
		EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);
		
		System.out.println(employeeService.getEmployee().getName());
		
		employeeService.getEmployee().setName("xzj");
		
		//employeeService.getEmployee().throwException();
		
		ctx.close();
	}
	
	// 您可以看到 advices 根据其切入点配置(pointcut configurations)逐个执行。 
	// 您应该逐个配置它们以避免混淆。
	
	// Note that: 打印最后会抛java.lang.RuntimeException: Dummy Exception，这个是正常的
}
