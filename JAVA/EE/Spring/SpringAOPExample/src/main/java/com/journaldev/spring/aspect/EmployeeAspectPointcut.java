package com.journaldev.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

// 有时我们必须在多个地方使用相同的Pointcut expression 表达式，
// 我们可以使用@Pointcut注释创建一个空方法，然后将其用作advices中的表达式。

@Aspect
public class EmployeeAspectPointcut {

	// 下面的示例非常清楚，我们在advice annotation参数中使用方法名称,而不是表达式，

	
	// 在advice annotation注解 @Before参数中使用方法名称，而不是表达式
	// getNamePointcut是使用@Pointcut注释创建的空方法
	@Before("getNamePointcut()")
	public void loggingAdvice(){
		System.out.println("Executing loggingAdvice on getName()");
	}
	
	// getNamePointcut()是使用@Pointcut创建的的空方法
	@Before("getNamePointcut()")
	public void secondAdvice(){
		System.out.println("Executing secondAdvice on getName()");
	}
	
	// 凡是使用getNamePointcut()作为advice参数的，都会匹配到"public String getName())"
	// 匹配到com.journaldev.spring.model.Employee的getName()方法
	@Pointcut("execution(public String getName())")
	public void getNamePointcut(){}
	
	
	@Before("allMethodsPointcut()")
	public void allServiceMethodsAdvice(){
		System.out.println("Before executing service method");
	}
	
	// Pointcut to execute on all the methods of classes in a package
	// 在com.journaldev.spring.service包中的类的所有方法上执行.
	// 当前的实例是匹配到com.journaldev.spring.service.EmployeeService的
	// getEmployee()方法
	@Pointcut("within(com.journaldev.spring.service.*)")
	public void allMethodsPointcut(){
		// @Pointcut注解修饰的是空方法，里面的打印不会被执行
	}
	
}
