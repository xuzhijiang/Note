package com.journaldev.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

// myAdvice() method will advice only setName() method. This is a very safe 
// approach and whenever we want to apply the advice on any method, 
// all we need is to annotate it with Loggable annotation.

// myAdvice（）方法will only advice setName（）方法。 这是一种非常安全的方法，
// 每当我们想要对任何方法应用advice时，我们所需要的只是使用Loggable注释来注释它。

@Aspect
public class EmployeeAnnotationAspect {

	@Before("@annotation(com.journaldev.spring.aspect.Loggable)")
	public void myAdvice(){
		System.out.println("Executing myAdvice!!");
	}
	
}
