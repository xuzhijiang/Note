package com.journaldev.spring.service;

import com.journaldev.spring.bean.Employee;

// 由于我们不希望我们的服务具有直接的spring框架依赖性，让我们创建另一种形式的Employee Service类，
// 我们将使用post-init和pre-destroy spring生命周期方法，我们将在spring bean配置文件中配置它们。

//2. 在spring bean配置文件中为bean提供init-method和destroy-method属性值。
//这是推荐的方法，因为没有直接依赖于spring框架，我们可以创建自己的方法。
//
//请注意，init-init和pre-destroy方法都应该没有参数，但它们可以抛出异常。
//我们还需要从spring应用程序上下文中获取bean实例以进行这些方法调用。
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
			employee.setName("xzj");
		}
	}
}
