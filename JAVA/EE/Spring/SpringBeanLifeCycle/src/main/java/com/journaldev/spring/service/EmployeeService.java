package com.journaldev.spring.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import com.journaldev.spring.bean.Employee;

// 创建一个服务类，我们将实现post-init和pre-destroy方法的接口。

//1. 通过实现InitializingBean和DisposableBean接口 - 这两个接口都声明了一个方法，
//我们可以在其中初始化/关闭bean中的资源。对于后期初始化(post-initialization)，
//我们可以实现InitializingBean接口并提供afterPropertiesSet(）方法的实现。
//对于pre-destroy，我们可以实现DisposableBean接口并提供destroy(）方法的实现。
//这些方法是回调方法，类似于servlet侦听器实现。
//这种方法易于使用，但不推荐使用，因为它会在我们的bean实现中与Spring框架建立紧密耦合。
public class EmployeeService implements InitializingBean, DisposableBean{

	private Employee employee;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public EmployeeService(){
		System.out.println("EmployeeService no-args constructor called");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("EmployeeService Closing resources");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("EmployeeService initializing to dummy value");
		if(employee.getName() == null){
			employee.setName("xzj");
		}
	}
	
}
