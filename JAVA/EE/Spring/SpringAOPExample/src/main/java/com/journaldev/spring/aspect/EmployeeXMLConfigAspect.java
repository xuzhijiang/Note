package com.journaldev.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;


// 我总是喜欢注释，但我们也可以选择在spring配置文件中配置Aspect,查看spring.xml中的配置 

// 没有使用@Aspect注解
public class EmployeeXMLConfigAspect {

	public Object employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
		System.out.println("EmployeeXMLConfigAspect:: Before invoking getName() method");
		Object value = null;
		try {
			value = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("EmployeeXMLConfigAspect:: After invoking getName() method. Return value="+value);
		return value;
	}
}
