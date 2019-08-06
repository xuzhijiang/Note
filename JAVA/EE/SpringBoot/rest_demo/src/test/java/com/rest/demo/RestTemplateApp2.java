package com.rest.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.demo.constants.EmpRestURIConstants;
import com.rest.demo.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 我们可以使用RestTemplate来测试基于HTTP的restful Web service，它不支持HTTPS协议。
 *
 * RestTemplate类为不同的HTTP methods 提供overloaded methods，例如GET，POST，PUT，DELETE等。
 *
 * URI 						HTTP方法				DESCRIPTION(描述)
 * /rest/person 			GET					Get all persons from database
 * /rest/person/{id} 	GET					Get person by id
 * /rest/person 			POST				Add person to database
 * /rest/person 			PUT					Update person
 * /rest/person/{id} 	DELETE				Delete person by id
 */
public class RestTemplateApp2 {

	public static final String SERVER_URI = "http://localhost:9090";

	private static final RestTemplate restTemplate;

	static {
		restTemplate = new RestTemplate();

		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		Jaxb2RootElementHttpMessageConverter xmlConverter = new Jaxb2RootElementHttpMessageConverter();

		jsonConverter.setObjectMapper(new ObjectMapper());
		// 设置converter
		restTemplate.getMessageConverters().add(jsonConverter);
		restTemplate.getMessageConverters().add(xmlConverter);
	}

	public static void main(String args[]){
		testGetDummyEmployee();
		System.out.println("*****");
		testCreateEmployee();
		System.out.println("*****");
		testGetEmployee();
		System.out.println("*****");
		testGetAllEmployee();
		System.out.println("*****");
	}

	private static void testGetAllEmployee() {
		ResponseEntity<Employee[]> entity = restTemplate.getForEntity(SERVER_URI + EmpRestURIConstants.GET_ALL_EMP, Employee[].class);
		Employee[] emps = entity.getBody();
		List<Employee> list = Arrays.asList(emps);
		for(Employee e : list){
			printEmpData(e);
		}
	}

	private static void testCreateEmployee() {
		Employee emp = new Employee();
		emp.setId(1);emp.setName("xzj");

		ResponseEntity<Employee> responseEntity = restTemplate.postForEntity(SERVER_URI + EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
//		ResponseEntity<HttpStatus> responseEntity = restTemplate.postForEntity(SERVER_URI + EmpRestURIConstants.CREATE_EMP, emp, HttpStatus.class);
		System.out.println(responseEntity.getStatusCode());
		printEmpData(responseEntity.getBody());
	}

	private static void testGetEmployee() {
		ResponseEntity<Employee> emp = restTemplate.getForEntity(SERVER_URI+"/rest/emp/1", Employee.class);
		printEmpData(emp.getBody());
	}

	private static void testGetDummyEmployee() {
		ResponseEntity<Employee>  entity = restTemplate.getForEntity(SERVER_URI+EmpRestURIConstants.DUMMY_EMP, Employee.class);
		printEmpData(entity.getBody());
	}
	
	public static void printEmpData(Employee emp){
		System.out.println("ID="+emp.getId()+",Name="+emp.getName()+",CreatedDate="+emp.getCreatedDate());
	}
}
