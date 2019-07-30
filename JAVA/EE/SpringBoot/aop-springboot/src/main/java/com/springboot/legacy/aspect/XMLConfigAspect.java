package com.springboot.legacy.aspect;

import org.aspectj.lang.ProceedingJoinPoint;


// 我总是喜欢注释，但也可以选择在xml配置文件中配置Aspect
// 没有使用@Aspect注解
public class XMLConfigAspect {

	public Object employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
		System.out.println("XMLConfigAspect:: Before invoking getName() method");
		Object value = null;
		try {
			value = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("XMLConfigAspect:: After invoking getName() method. Return value="+value);
		return value;
	}
}
