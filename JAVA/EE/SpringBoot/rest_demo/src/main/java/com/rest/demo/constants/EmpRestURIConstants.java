package com.rest.demo.constants;

// rest web services end points(服务端点):
public class EmpRestURIConstants {

	public static final String DUMMY_EMP = "/rest/emp/dummy";
	
	public static final String GET_EMP = "/rest/emp/{id}"; // GET
	
	public static final String GET_ALL_EMP = "/rest/emps"; // GET
	
	public static final String CREATE_EMP = "/rest/emp/create"; // POST
	
	public static final String DELETE_EMP = "/rest/emp/delete/{id}"; // PUT
}