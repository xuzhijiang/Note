package com.journaldev.spring.service;

import com.journaldev.spring.model.Employee;

// 创建一个服务类来使用Employee bean。
// create a service class to work with Employee bean.

// I could have used Spring annotations to configure it as 
// a Spring Component, but we will use XML based configuration 
// in this project. EmployeeService class is very standard and 
// just provides us an access point for Employee beans.

// 我本可以使用Spring annotations将其配置为Spring组件，
// 但我们将在此项目中使用基于XML的配置。 EmployeeService类非常标准，只是为Employee bean提供了一个访问点。
public class EmployeeService {

	private Employee employee;
	
	public Employee getEmployee(){
		return this.employee;
	}
	
	public void setEmployee(Employee e){
		this.employee=e;
	}
}
