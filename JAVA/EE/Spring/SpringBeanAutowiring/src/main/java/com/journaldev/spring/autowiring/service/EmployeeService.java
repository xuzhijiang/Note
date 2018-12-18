package com.journaldev.spring.autowiring.service;

import com.journaldev.spring.autowiring.model.Employee;

// 创建我们的服务类，我们将通过spring自动装配注入Employee bean 到此服务

// 我们将使用相同的服务类来执行Spring自动装配byName，byType和构造函数。 
// setter方法将用于Spring自动装配byName和byType，而基于构造函数的注入将由构造函数autowire属性使用。

// 当我们使用spring autowire byName或byType时，使用默认构造函数。 
// 这就是我们为EmployeeService bean明确定义默认构造函数的原因。
public class EmployeeService {

	private Employee employee;

	// constructor is used for autowiring by constructor
	public EmployeeService(Employee emp) {
		System.out.println("Autowiring by constructor used");
		this.employee = emp;
	}

	// default constructor to avoid BeanInstantiationException for autowiring
	// byName or byType
	public EmployeeService() {
		System.out.println("Default Constructor used");
	}

	// used for autowire byName and byType
	public void setEmployee(Employee emp) {
		this.employee = emp;
	}

	public Employee getEmployee() {
		return this.employee;
	}
}
