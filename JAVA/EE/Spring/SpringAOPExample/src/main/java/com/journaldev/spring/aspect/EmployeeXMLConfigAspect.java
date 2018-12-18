package com.journaldev.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

// I always prefer annotation but we also have option to 
// configure aspects in spring configuration file.

// 我总是喜欢注释，但我们也可以选择在spring配置文件中配置方面。 
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
