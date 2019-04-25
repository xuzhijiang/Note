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

		// 会触发EmployeeAspect的getAllAdvice method和getNameAdvice method.
		System.out.println("------: " + employeeService.getEmployee().getName());

		//Employee employee = ctx.getBean("employee", Employee.class);

		//System.out.println("------: " + employee.getName());

		//System.out.println("------: " + (employee == employeeService.getEmployee()));//true

		Employee employee2 = new Employee();
		employee2.setName("new operator create employee2 name");
		System.out.println(employee2.getName());

		// 会触发EmployeeAspect的getAllAdvice method.
		employeeService.getEmployee().setName("xzj");

		// 会触发EmployeeAspect的getAllAdvice method.
		employeeService.getEmployee().throwException();
		
		ctx.close();
		
		// 从console可以看到advices 根据其切入点配置(pointcut configurations)逐个执行。
	}
	
}
