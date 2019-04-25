package com.journaldev.spring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.journaldev.spring.service.EmployeeService;

/*Spring Bean Life Cycle Important Points:

1. 从控制台输出中可以清楚地看到，Spring Context首先使用no-args构造函数来初始化bean对象，
然后调用post-init方法。

2. bean初始化的顺序与bean在spring bean配置文件中定义的顺序相同。

3. 仅当所有spring bean使用post-init方法执行正确初始化后，才返回上下文context。

4. 员工姓名打印为“xzj”，因为它是在post-init方法中初始化的。(参照MyEmployeeService)

5. 当上下文被关闭时，bean按照它们被初始化的相反顺序被销毁，即以LIFO(后进先出）顺序。

*/

public class SpringMainEmployeeService {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		System.out.println("Spring Context initialized");
		
		EmployeeService service = ctx.getBean("employeeService", EmployeeService.class);

		System.out.println("Bean retrieved from Spring Context");
		
		System.out.println("Employee Name="+service.getEmployee().getName());
		
		ctx.close();
		System.out.println("Spring Context Closed");
	}

}
