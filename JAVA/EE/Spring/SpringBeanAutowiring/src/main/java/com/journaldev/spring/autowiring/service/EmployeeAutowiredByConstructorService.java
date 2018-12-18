package com.journaldev.spring.autowiring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.journaldev.spring.autowiring.model.Employee;

//让我们创建另一个服务类，我们将使用@Autowired注释进行基于构造函数的注入。 
// 我们还将看到@Qualifier注释用法。


//当这个bean将被Spring框架初始化时，名为“employee”的bean将用于自动装配。 
// Spring @Autowired注释除了一个参数“required”，它是一个布尔值，默认值为TRUE。 
// 我们可以将它定义为“false”，这样如果找不到适合自动装配的bean，spring框架就不会抛出任何异常。
public class EmployeeAutowiredByConstructorService {

	private Employee employee;

	//Autowired annotation on Constructor is equivalent to autowire="constructor"
	@Autowired(required=false)
	public EmployeeAutowiredByConstructorService(@Qualifier("employee") Employee emp){
		this.employee=emp;
	}
	
	
	public Employee getEmployee() {
		return this.employee;
	}
}
