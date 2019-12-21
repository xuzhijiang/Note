package com.springboot.legacy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy // Standalone应用需要添加这个注解支持
public class AnnotationAspect {

	@Pointcut("@annotation(com.springboot.legacy.annotation.Loggable)")
	public void pc() {}

	// 将会应用于使用Loggable注解的method,在执行方法之前调用(因为使用的是before)
	@Before("pc()")
	public void myAdvice(JoinPoint joinPoint) throws Throwable { // ProceedingJoinPoint只作用于Around
		System.out.println("AnnotationAspect!!");
	}
	
}
