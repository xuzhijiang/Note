package com.journaldev.spring.jdbc.dao;

import java.util.List;

import com.journaldev.spring.jdbc.model.Employee;
//对于DAO模式，我们首先会有一个接口，声明我们想要实现的所有操作。
//CRUD operations
public interface EmployeeDAO {
	
	//Create
	public void save(Employee employee);
	//Read
	public Employee getById(int id);
	//Update
	public void update(Employee employee);
	//Delete
	public void deleteById(int id);
	//Get All
	public List<Employee> getAll();
}
