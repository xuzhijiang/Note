package com.journaldev;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import com.journaldev.config.AppConfig;
import com.journaldev.drivers.DataBaseDriver;
import com.journaldev.service.UserService;

// 我们的spring注释示例项目已准备好进行测试。 总结一下，我们执行了以下步骤：

// 1. 创建了maven项目并添加了所需的spring依赖项。
// 2. 创建组件类并将资源文件中的属性注入其变量中。
// 3. 如果我们有第三方组件，我们可以使用Service类将依赖项注入其中。 
// 就像我们通过UserService类为OracleDriver所做的那样。
// 4. 最后，我们创建了Configuration类来定义spring bean并设置基本包以扫描spring组件类, 并对其进行配置。
public class Main {
	public static void main(String[] args) {
		AbstractApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

		DataBaseDriver oracle = appContext.getBean("oracleDriver", DataBaseDriver.class);
		DataBaseDriver mysql = appContext.getBean("mysqlDriver", DataBaseDriver.class);
		
        System.out.println("Oracle driver info:");
        System.out.println(oracle.getInfo());
        
        System.out.println("MySQL driver info:");
        System.out.println(mysql.getInfo());

        System.out.println("UserService Information");
		UserService userService = appContext.getBean(UserService.class);
		System.out.println(userService.getDriverInfo());

		appContext.close();
	}
}
