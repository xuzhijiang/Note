package com.journaldev.spring.controller;

// rest web services end points(rest web 服务端点):

// URI						HTTP METHOD			DETAILS
///rest/emp/dummy			GET					Health Check service, to insert a dummy data in the Employees data storage
///rest/emp/{id}			GET					To get the Employee object based on the id
///rest/emps				GET					To get the list of all the Employees in the data store
///rest/emp/create			POST				To create the Employee object and store it
///rest/emp/delete/{id}		PUT					To delete the Employee object from the data storage based on the id

public class EmpRestURIConstants {

	// 运行状况检查服务(Health check service)，用于在Employees数据存储中插入虚拟数据
	public static final String DUMMY_EMP = "/rest/emp/dummy";
	
	public static final String GET_EMP = "/rest/emp/{id}";
	
	public static final String GET_ALL_EMP = "/rest/emps";
	
	public static final String CREATE_EMP = "/rest/emp/create";
	
	public static final String DELETE_EMP = "/rest/emp/delete/{id}";

	// http://localhost:9090/SpringRestExample/rest/emp/create
	// Create Employee POST Rest Call:
	// Make sure request Content-Type is set to “application/json” otherwise 
	// you will get HTTP Error Code 415.
}