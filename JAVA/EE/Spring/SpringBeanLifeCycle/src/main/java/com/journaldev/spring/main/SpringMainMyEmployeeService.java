package com.journaldev.spring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.journaldev.spring.service.MyEmployeeService;

public class SpringMainMyEmployeeService {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		// 执行到这里之前，spring.xml中的bean都会被创建，等待下面使用
		System.out.println("Spring Context initialized");
		
		MyEmployeeService service = ctx.getBean("myEmployeeService", MyEmployeeService.class);

		System.out.println("Bean retrieved from Spring Context");
		
		System.out.println("Employee Name="+service.getEmployee().getName());
		
		ctx.close();
		
		System.out.println("Spring Context Closed");
	}

}
