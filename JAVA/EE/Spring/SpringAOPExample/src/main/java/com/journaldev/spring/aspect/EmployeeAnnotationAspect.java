package com.journaldev.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class EmployeeAnnotationAspect {

	// myAdvice（）方法will only advice setName（）方法。 这是一种非常安全的方法，
	// 每当我们想要对任何方法应用advice时，我们所需要的只是使用Loggable注释来注释它。
	// 将会应用于使用Loggable注解的method,在执行com.journaldev.spring.model.Employee.setName方法之前调用
	@Before("@annotation(com.journaldev.spring.aspect.Loggable)")
	public void myAdvice(){
		System.out.println("Executing myAdvice!!");
	}
	
}
