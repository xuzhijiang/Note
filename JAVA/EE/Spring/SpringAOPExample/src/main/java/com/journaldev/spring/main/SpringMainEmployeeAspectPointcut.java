package com.journaldev.spring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.model.Employee;
import com.journaldev.spring.service.EmployeeService;

public class SpringMainEmployeeAspectPointcut {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		// 参数1：要检索的bean的名字
		EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);
		
		System.out.println("1-----------Dividing line----------");
		
		System.out.println(employeeService.getEmployee());
		
		System.out.println("2-----------Dividing line----------");
		
		System.out.println(employeeService.getEmployee().getName());
		
		System.out.println("3-----------Dividing line----------");
		
		ctx.close();
		
		// 从console可以看到advices 根据其切入点配置(pointcut configurations)逐个执行。
	}
	
}
