package com.journaldev.spring.autowiring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.journaldev.spring.autowiring.service.EmployeeService;

public class SpringMainByConstructor {

	public static void main(String[] args) {
		
		// 我们只是创建spring应用程序上下文, 并使用它来获取不同的bean并打印员工姓名。
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		EmployeeService serviceByConstructor = ctx.getBean("employeeServiceConstructor", EmployeeService.class);
		
		// System.out.println("Autowiring by Constructor. Employee Name="+serviceByConstructor.getEmployee().getName());

		ctx.close();
	}

}