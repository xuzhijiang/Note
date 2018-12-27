package com.journaldev.spring;

import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import com.journaldev.spring.controller.EmpRestURIConstants;
import com.journaldev.spring.model.Employee;

// Spring Rest Client Program


/*
	Rest Clients可以测试我们的rest web service，
	但大多数时候，我们需要通过我们的程序调用invoke rest web service。 
	我们可以使用Spring RestTemplate轻松调用这些方法。 
	下面是一个使用RestTemplate API调用我们的应用程序rest method的简单程序。
	
	大多数程序都很容易理解，但是当调用rest method返回Collection的时，
	我们需要使用LinkedHashMap，因为JSON到object的转换不知道Employee对象
	并将其转换为LinkedHashMap的集合。 
	我们可以编写一个实用工具(a utility)方法来将LinkedHashMap转换为我们的Java Bean对象。
	
	另一点是RestTemplate put方法没有设置响应对象response object的选项，
	因为PUT方法应该用于在服务器上存储某些东西，
	而简单的HTTP 200状态代码就足够了。
*/
public class TestSpringRestExample {

	public static final String SERVER_URI = "http://localhost:9090/SpringRestExample";
	
	public static void main(String args[]){
		testGetDummyEmployee();
		System.out.println("*****");
		testCreateEmployee();
		System.out.println("*****");
		testGetEmployee();
		System.out.println("*****");
		testGetAllEmployee();
	}

	private static void testGetAllEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		//we can't get List<Employee> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.GET_ALL_EMP, List.class);
		System.out.println(emps.size());
		for(LinkedHashMap map : emps){
			System.out.println("ID="+map.get("id")+",Name="+map.get("name")+",CreatedDate="+map.get("createdDate"));;
		}
	}

	private static void testCreateEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = new Employee();
		emp.setId(1);emp.setName("Pankaj Kumar");
		Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
		printEmpData(response);
	}

	private static void testGetEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = restTemplate.getForObject(SERVER_URI+"/rest/emp/1", Employee.class);
		printEmpData(emp);
	}

	private static void testGetDummyEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.DUMMY_EMP, Employee.class);
		printEmpData(emp);
	}
	
	public static void printEmpData(Employee emp){
		System.out.println("ID="+emp.getId()+",Name="+emp.getName()+",CreatedDate="+emp.getCreatedDate());
	}
}
