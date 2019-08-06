package com.rest.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.demo.constants.EmpRestURIConstants;
import com.rest.demo.domain.Employee;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.LinkedHashMap;
import java.util.List;

public class RestTemplateApplication {

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
		// 大多数程序都很容易理解，但是当返回Collection的时，
		// 我们需要使用LinkedHashMap，因为JSON 转换器不知道list中的对象的类型.
		// 因此将list中的对象转换成了默认的LinkedHashMap
		//we can't get List<Employee> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		List<LinkedHashMap> list = restTemplate.getForObject(SERVER_URI+ EmpRestURIConstants.GET_ALL_EMP, List.class);
		System.out.println("list size: " + list.size());
		for(LinkedHashMap map : list){
			System.out.println("ID="+map.get("id")+",Name="+map.get("name")+",CreatedDate="+map.get("createdDate"));
		}
	}

	private static void testCreateEmployee() {
		Employee emp = new Employee();
		emp.setId(1);emp.setName("xzj");
		Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
		printEmpData(response);
	}

	private static void testGetEmployee() {
		Employee emp = restTemplate.getForObject(SERVER_URI+"/rest/emp/1", Employee.class);
		printEmpData(emp);
	}

	private static void testGetDummyEmployee() {
		Employee emp = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.DUMMY_EMP, Employee.class);
		printEmpData(emp);
	}
	
	public static void printEmpData(Employee emp){
		System.out.println("ID="+emp.getId()+",Name="+emp.getName()+",CreatedDate="+emp.getCreatedDate());
	}
}
