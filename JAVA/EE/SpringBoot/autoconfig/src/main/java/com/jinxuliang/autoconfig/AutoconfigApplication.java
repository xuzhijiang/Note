package com.jinxuliang.autoconfig;

import com.jinxuliang.autoconfig.dao.UserDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AutoconfigApplication {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(AutoconfigApplication.class, args);
		testConditionalConfig(context);
	}

	private static void testConditionalConfig(ApplicationContext context) {
		UserDAO dao=context.getBean(UserDAO.class);
		System.out.println(dao.getAllUserNames());
	}
}
