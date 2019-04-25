package com.journaldev.spring.autowiring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.journaldev.spring.autowiring.service.EmployeeService;

//1. autowire byName  - 对于这种类型的自动装配，setter方法(EmployeeService的setter)用于依赖注入。
//在我们将注入依赖项(Employee是EmployeeService的依赖项)的类(EmployeeService)
// 和spring bean配置文件中，变量名也应该相同。
public class SpringMainByName {

	public static void main(String[] args) {
		
		// 我们只是创建spring应用程序上下文, 并使用它来获取不同的bean并打印员工姓名。
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		EmployeeService serviceByName = ctx.getBean("employeeServiceByName", EmployeeService.class);
		
		System.out.println("Autowiring byName. Employee Name="+serviceByName.getEmployee().getName());
		
		ctx.close();
	}

}
