package com.journaldev.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class EmployeeAfterAspect {

	// Employee.setName() method执行之后
	@After("args(name)")
	public void logStringArguments(String name){
		System.out.println("Running After Advice. String argument passed="+name);
	}
	
	// 我们可以在pointcut expression(切入点表达式)中
	// 使用"within" 来将advice应用于类中的所有方法。
	
	// 我们在Employee bean中有throwException(）方法来展示 "After Throwing device"的使用。
	// 当com.journaldev.spring.model.Employee中抛出异常的时候调用此方法
	@AfterThrowing("within(com.journaldev.spring.model.Employee)")
	public void logExceptions(JoinPoint joinPoint){
		System.out.println("Exception thrown in Employee Method="+joinPoint.toString());
	}
	
	// 我们可以使用@AfterReturning device 来获取deviced method返回的对象。
	// 本例中应用于Employee.getName() method
	@AfterReturning(pointcut="execution(* getName())", returning="returnString")
	public void getNameReturningAdvice(String returnString){
		System.out.println("getNameReturningAdvice executed. Returned String="+returnString);
	}
	
}
