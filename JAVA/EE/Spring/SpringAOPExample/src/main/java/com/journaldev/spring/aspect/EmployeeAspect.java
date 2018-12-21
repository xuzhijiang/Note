package com.journaldev.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 在下面的切面类重要的几点是：
 */

// 切面类要求有@Aspect注解(Aspect classes are required to have @Aspect annotation.)

@Aspect
public class EmployeeAspect {

	// @Before annotation用于创建Before Advice(@Before annotation is used to create Before advice)
	// @Before annotation中传递的字符串参数是Pointcut表达式
	@Before("execution(public String getName())")
	public void getNameAdvice(){
		System.out.println("Executing Advice on getName()");
	}
	
	 /* 4. getNameAdvice（）advice将使用签名public String getName（）
	 * 执行任何Spring Bean方法。 这是一个非常重要的要点，如果我们使用new运算符创建
	 * Employee bean，则不会应用advice。 只有当我们使用ApplicationContext来获取bean时，才会应用advice。
	  */
	
	 /* 5. 我们可以在Pointcut表达式中使用asterisk(*) 星号（*）作为通配符(wild card)，
	 * getAllAdvice（）将应用于com.journaldev.spring.service包中的其名称以get开头并且不带任何参数所有类，*/
	@Before("execution(* com.journaldev.spring.service.*.get*())")
	public void getAllAdvice(){
		System.out.println("Service method getter called");
	}
	
}
