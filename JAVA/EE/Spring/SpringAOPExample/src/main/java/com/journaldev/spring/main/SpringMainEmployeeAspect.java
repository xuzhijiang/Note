package com.journaldev.spring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.model.Employee;
import com.journaldev.spring.service.EmployeeService;

// 看看这些aspects如何切入bean方法:

public class SpringMainEmployeeAspect {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		// 参数1：要检索的bean的名字
		EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);
		
		System.out.println("1-----------Dividing line----------");
		
		System.out.println(employeeService.getEmployee());
		
		System.out.println("2-----------Dividing line----------");
		
		System.out.println(employeeService.getEmployee().getName());
		
		System.out.println("3-----------Dividing line----------");
		
		Employee employee = ctx.getBean("employee", Employee.class);
		
		System.out.println(employee.getName());
		
		System.out.println("4-----------Dividing line----------");

		Employee employee2 = new Employee();
		employee2.setName("new operator create employee2 name");
		System.out.println(employee2.getName());
		
		System.out.println("5-----------Dividing line----------");
		
		employeeService.getEmployee().setName("xzj");
		
		employeeService.getEmployee().throwException();
		
		ctx.close();
		
		// 从console可以看到advices 根据其切入点配置(pointcut configurations)逐个执行。
	}
	
}
