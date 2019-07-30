package com.springboot.legacy.service;

import com.springboot.legacy.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private Employee employee;

	public Employee getEmployee(){
		return this.employee;
	}

	public void setEmployee(Employee e){
		this.employee=e;
	}

}
