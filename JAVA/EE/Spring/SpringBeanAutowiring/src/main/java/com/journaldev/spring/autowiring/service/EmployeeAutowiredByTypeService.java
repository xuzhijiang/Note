package com.journaldev.spring.autowiring.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.journaldev.spring.autowiring.model.Employee;

// 创建一个单独的类,并且用Spring @Autowired注释用于自动装配byType。

public class EmployeeAutowiredByTypeService {

	// 1. 请注意，我已经使用Spring @Autowired注解注释了Employee变量和它的setter方法，
	// 2. 但是只有其中一个足以用于spring bean自动装配。
	
	// Autowired annotation on variable/setters is equivalent to autowire="byType"
	// 在变量/setter上的自动注释相当于autowire =“byType”
	@Autowired
	private Employee employee;
	
	@Autowired
	public void setEmployee(Employee emp){
		System.out.println("-------EmployeeAutowiredByTypeService setEmployee called");
		this.employee=emp;
	}
	
	public Employee getEmployee(){
		System.out.println("-------EmployeeAutowiredByTypeService getEmployee called");
		return this.employee;
	}
}
