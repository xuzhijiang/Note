package com.journaldev.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

// Sometimes we have to use same Pointcut expression at multiple 
// places, we can create an empty method with @Pointcut annotation 
// and then use it as expression in advices.

// 有时我们必须在多个地方使用相同的Pointcut expression 表达式，
// 我们可以使用@Pointcut注释创建一个空方法，然后将其用作advices中的表达式。
@Aspect
public class EmployeeAspectPointcut {

	@Before("getNamePointcut()")
	public void loggingAdvice(){
		System.out.println("Executing loggingAdvice on getName()");
	}
	
	@Before("getNamePointcut()")
	public void secondAdvice(){
		System.out.println("Executing secondAdvice on getName()");
	}
	
	@Pointcut("execution(public String getName())")
	public void getNamePointcut(){}
	
	@Before("allMethodsPointcut()")
	public void allServiceMethodsAdvice(){
		System.out.println("Before executing service method");
	}
	
	//Pointcut to execute on all the methods of classes in a package
	@Pointcut("within(com.journaldev.spring.service.*)")
	public void allMethodsPointcut(){}
	
}
// Above example is very clear, rather than expression we are using 
// method name in the advice annotation argument.
// 上面的示例非常清楚，而不是表达式，我们在advice annotation参数中使用方法名称,而不是表达式，