package com.legacy.springmvc.jdbc.main;

import java.util.List;
import java.util.Random;

import com.legacy.springmvc.jdbc.dao.EmployeeDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.legacy.springmvc.jdbc.model.Employee;

public class SpringMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring.xml");
		
		//EmployeeDAO employeeDAO = ctx.getBean("employeeDAO", EmployeeDAO.class);
		EmployeeDAO employeeDAO = ctx.getBean("employeeDAOJDBCTemplate", EmployeeDAO.class);
		
		Employee emp = new Employee();
		int rand = new Random().nextInt(1000);
		emp.setId(rand);
		emp.setName("Pankaj");
		emp.setRole("Java Developer");
		
		//Create
		employeeDAO.save(emp);
		
		Employee emp1 = employeeDAO.getById(rand);
		System.out.println("Employee Retrieved::"+emp1);
		
		//Update
		emp.setRole("CEO");
		employeeDAO.update(emp);
		
		//Get All
		List<Employee> empList = employeeDAO.getAll();
		System.out.println(empList);
		
		//Delete
		employeeDAO.deleteById(rand);
		
		ctx.close();
		System.out.println("DONE");
	}

}
