package com.journaldev.spring.jdbc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.journaldev.spring.jdbc.model.Employee;

// 关于Controller类的重点是：

// 1. DataSource将通过名为dbDataSource的Spring Bean配置wired(连接)

// 2. 我们正在使用JdbcTemplate来避免资源泄漏等常见错误并删除JDBC样板代码。

// 3. 检索Employee列表的URI将是http//{host}{port}/SpringDataSource/rest/emps

// 4. 我们使用@ResponseBody发送Employee对象列表作为响应，Spring将负责将其转换为JSON。

/**
 * Handles requests for the Employee JDBC Service.
 */
@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	@Qualifier("dbDataSource")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@RequestMapping(value = "/rest/emps", method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees() {
		logger.info("Start getAllEmployees.");
		List<Employee> empList = new ArrayList<Employee>();
		//JDBC Code - Start
		String query = "select id, name, role from Employee";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<Map<String,Object>> empRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> empRow : empRows){
			Employee emp = new Employee();
			emp.setId(Integer.parseInt(String.valueOf(empRow.get("id"))));
			emp.setName(String.valueOf(empRow.get("name")));
			emp.setRole(String.valueOf(empRow.get("role")));
			empList.add(emp);
		}
		
		return empList;
	}

}
