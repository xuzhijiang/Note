package com.journaldev.spring.autowiring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.journaldev.spring.autowiring.service.EmployeeAutowiredByTypeService;

public class SpringMainAutowiredByTypeService {

	public static void main(String[] args) {
		
		// 我们只是创建spring应用程序上下文, 并使用它来获取不同的bean并打印员工姓名。
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		//Testing @Autowired annotations
		EmployeeAutowiredByTypeService autowiredByTypeService = ctx.getBean("employeeAutowiredByTypeService",EmployeeAutowiredByTypeService.class);
		
		//System.out.println("@Autowired byType. Employee Name="+autowiredByTypeService.getEmployee().getName());

		ctx.close();
	}

}
