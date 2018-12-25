package com.journaldev.spring.autowiring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.journaldev.spring.autowiring.service.EmployeeAutowiredByConstructorService;

public class SpringMainAutowiredByConstructorService {

	public static void main(String[] args) {
		
		// 我们只是创建spring应用程序上下文, 并使用它来获取不同的bean并打印员工姓名。
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		EmployeeAutowiredByConstructorService autowiredByConstructorService = ctx.getBean("employeeAutowiredByConstructorService",EmployeeAutowiredByConstructorService.class);
		
		System.out.println("@Autowired by Constructor. Employee Name="+autowiredByConstructorService.getEmployee().getName());

		ctx.close();
	}

}
