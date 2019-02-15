package com.journaldev;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import com.journaldev.config.AppConfig;
import com.journaldev.drivers.DataBaseDriver;
import com.journaldev.service.UserService;

public class Main {
	public static void main(String[] args) {
		AbstractApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

		// getBean的name参数是AppConfig中使用@Bean注解的方法名称.
		DataBaseDriver oracle = appContext.getBean("rrr", DataBaseDriver.class);
		DataBaseDriver mysql = appContext.getBean("mmm", DataBaseDriver.class);
		
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
