package com.spring.condition.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;

@SpringBootApplication
public class ConditionAnnotation {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ConditionAnnotation.class, args);
		UserDAO dao=context.getBean(UserDAO.class);
		System.out.println(dao.getAllUserNames());
	}
}
