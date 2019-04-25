package com.journaldev.spring.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

// 我们可以使用JoinPoint作为 advice methods中的参数，
// 并使用它(JoinPoint)来获取方法签名或目标对象。

@Aspect
public class EmployeeAspectJoinPoint {

	// 1. 本例中，将应用于com.journaldev.spring.model.Employee中的setName方法
	// 2. 直接调用EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);不会调用此advice method
	@Before("execution(public void com.journaldev.spring.model..set*(*))")
	public void loggingAdvice(JoinPoint joinPoint){
		System.out.println("Before running loggingAdvice on method="+joinPoint.toString());
		
		System.out.println("Agruments Passed=" + Arrays.toString(joinPoint.getArgs()));
	}
	
	
	// 我们可以在PointCut中使用args()表达式来应用于"与bean(Employee) method中参数模式匹配(参数个数,参数类型)"的任何方法。
	// 如果我们使用它，那么我们需要在确定参数类型的advice方法中使用相同的名称。 
	// 我们也可以在advice参数中使用Generic对象。
	
	// 1. Advice arguments, will be applied to bean methods with single String argument
	// 2. 将会应用于只有一个String参数的bean method
	@Before("args(name)")
	public void logStringArguments(String name){
		System.out.println("String argument passed="+name);
	}
	
}
