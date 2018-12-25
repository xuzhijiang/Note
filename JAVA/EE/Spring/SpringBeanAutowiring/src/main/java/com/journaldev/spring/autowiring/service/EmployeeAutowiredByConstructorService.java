package com.journaldev.spring.autowiring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.journaldev.spring.autowiring.model.Employee;

// 1. 创建另一个服务类，我们将使用@Autowired注释进行基于构造函数的注入。 

// 我们还将看到@Qualifier注释用法。

// 2. 当这个bean将被Spring框架初始化时，name为“employee”的bean将用于自动装配。 
// 3. Spring @Autowired注释除了一个参数“required”，它是一个布尔值，默认值为TRUE。 
// 4. 我们可以将它(required)定义为“false”，这样如果找不到适合自动装配的bean，spring框架就不会抛出任何异常。

public class EmployeeAutowiredByConstructorService {

	private Employee employee;

	//	6. @Qualifier注释 - 此注释用于避免bean映射中的冲突，我们需要为此注解提供将用于自动装配的bean的name。
	//	这样我们就可以避免为同一类型(Employee)定义多个bean(employee和employee1)的问题。
	// 此注释通常与@Autowired注释一起使用。
	// 对于具有多个参数的构造函数，我们可以将此注释与方法中的参数名称一起使用。
	
	//Autowired annotation on Constructor is equivalent to autowire="constructor"
	@Autowired(required=false)
	public EmployeeAutowiredByConstructorService(@Qualifier("employee") Employee emp){
		System.out.println("-------EmployeeAutowiredByConstructorService constructor called");
		this.employee=emp;
	}
	
	public Employee getEmployee() {
		System.out.println("-------EmployeeAutowiredByConstructorService getEmployee called");
		return this.employee;
	}
}
