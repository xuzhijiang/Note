package com.journaldev.spring.service;

import com.journaldev.spring.bean.Employee;

// 由于我们不希望我们的服务具有直接的spring框架依赖性，让我们创建另一种形式的Employee Service类，
// 我们将使用post-init和pre-destroy spring生命周期方法，我们将在spring bean配置文件中配置它们。

public class MyEmployeeService{

	private Employee employee;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public MyEmployeeService(){
		System.out.println("MyEmployeeService no-args constructor called");
	}

	public void destroy() throws Exception {
		System.out.println("MyEmployeeService Closing resources");
	}

	public void init() throws Exception {
		System.out.println("MyEmployeeService initializing to dummy value");
		if(employee.getName() == null){
			employee.setName("Pankaj");
		}
	}
}
