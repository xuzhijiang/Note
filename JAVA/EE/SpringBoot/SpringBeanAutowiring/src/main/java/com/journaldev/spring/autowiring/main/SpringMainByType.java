package com.journaldev.spring.autowiring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.journaldev.spring.autowiring.service.EmployeeService;

//2. autowire byType  - 对于这种类型的自动装配，使用类类型(class type,本例中即Employee)自动装配。
//因此在spring bean配置文件中应该只有一个为此类型(for this type,即Employee)配置的bean。
public class SpringMainByType {

	public static void main(String[] args) {
		// 我们只是创建spring应用程序上下文, 并使用它来获取不同的bean并打印员工姓名。
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		EmployeeService serviceByType = ctx.getBean("employeeServiceByType", EmployeeService.class);
		
		System.out.println("Autowiring byType. Employee Name="+serviceByType.getEmployee().getName());

		ctx.close();
	}

}
