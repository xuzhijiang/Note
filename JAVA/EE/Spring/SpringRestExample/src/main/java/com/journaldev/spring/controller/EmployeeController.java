package com.journaldev.spring.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.journaldev.spring.model.Employee;

/*
EmployeeController类将发布上面提到的所有Web服务端点(service end points)
*/

/**
 * Handles requests for the Employee service.
 */
@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	// 为简单起见，我将所有employee数据存储在HashMap empData中.
	//Map to store employees, ideally we should use database
	Map<Integer, Employee> empData = new HashMap<Integer, Employee>();
	
	// @RequestMapping注释用于将请求URI映射到处理程序方法。
	// 我们还可以指定客户端应用程序应该使用的HTTP方法来调用rest方法( to invoke the rest method)。
	@RequestMapping(value = EmpRestURIConstants.DUMMY_EMP, method = RequestMethod.GET)
	public @ResponseBody Employee getDummyEmployee() {
		logger.info("Start getDummyEmployee");
		Employee emp = new Employee();
		emp.setId(9999);
		emp.setName("Dummy");
		emp.setCreatedDate(new Date());
		empData.put(9999, emp);
		return emp;
	}
	
	// @ResponseBody注释用于映射response object中的response body
	// 一旦handler method返回响应对象，MappingJackson2HttpMessageConverter
	// 就会启动(kicks in)并将其转换为JSON响应。
	@RequestMapping(value = EmpRestURIConstants.GET_EMP, method = RequestMethod.GET)
	public @ResponseBody Employee getEmployee(@PathVariable("id") int empId) {
		logger.info("Start getEmployee. ID="+empId);
		
		return empData.get(empId);
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP, method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees() {
		logger.info("Start getAllEmployees.");
		List<Employee> emps = new ArrayList<Employee>();
		Set<Integer> empIdKeys = empData.keySet();
		for(Integer i : empIdKeys){
			emps.add(empData.get(i));
		}
		return emps;
	}
	
	// @RequestBody注释用于将"请求主体JSON数据(request body JSON data)"映射到Employee对象，
	// 这也是由MappingJackson2HttpMessageConverter映射完成的。
	// again this is done by the MappingJackson2HttpMessageConverter mapping.
	@RequestMapping(value = EmpRestURIConstants.CREATE_EMP, method = RequestMethod.POST)
	public @ResponseBody Employee createEmployee(@RequestBody Employee emp) {
		logger.info("Start createEmployee.");
		emp.setCreatedDate(new Date());
		empData.put(emp.getId(), emp);
		return emp;
	}
	
	// @PathVariable注释是从rest URI中提取数据并将其映射到method argument的简单方法。
	@RequestMapping(value = EmpRestURIConstants.DELETE_EMP, method = RequestMethod.PUT)
	public @ResponseBody Employee deleteEmployee(@PathVariable("id") int empId) {
		logger.info("Start deleteEmployee.");
		Employee emp = empData.get(empId);
		empData.remove(empId);
		return emp;
	}
	
}

// 只需导出为WAR文件并将其复制到servlet容器Web应用程序目录中。
// 如果您在STS中配置了服务器，则只需在服务器上运行它即可部署它。

//我正在使用WizTools RestClient来调用the rest calls，但您也可以使用Chrome的扩展Postman。
