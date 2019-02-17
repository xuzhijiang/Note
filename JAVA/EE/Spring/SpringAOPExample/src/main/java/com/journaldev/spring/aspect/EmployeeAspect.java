package com.journaldev.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 在下面的切面类重要的几点是:
*/

// asterisk: 星号

// (Aspect classes are required to have @Aspect annotation.)
// 切面类要求有@Aspect注解

@Aspect
public class EmployeeAspect {

	// 1. @Before annotation用于创建Before Advice(@Before annotation is used to create Before advice)
	// 2. @Before annotation中传递的字符串参数是Pointcut表达式(横切点表达式)
	// 3. getNameAdvice(）advice将使用签名"public String getName()" 来匹配Spring Bean中的方法。
	// 这是一个非常重要的要点，
	
	// 4. 如果我们使用new运算符创建Employee bean，然后调用Employee的getName()则不会应用advice。
	// 5. 只有当我们使用ApplicationContext来获取bean时，然后调用Employee bean的getName(),才会应用advice。
	// 6. 本例中，在Employee的getName()方法执行会调用getNameAdvice()
	@Before("execution(public String getName())")
	public void getNameAdvice(){
		System.out.println("Executing Advice on getName()");
	}
	
	 // 7. 我们可以在Pointcut表达式中使用asterisk(*)作为通配符(wild card)，
	 // 8. getAllAdvice(）将应用于com.journaldev.spring.service包中的其名称以get开头并且不带任何参数所有类
	// 9. 本例中将应用于com.journaldev.spring.service.EmployeeService类中的getEmployee method.
	@Before("execution(* com.journaldev.spring.service.*.get*())")
	public void getAllAdvice(){
		System.out.println("Service method getter called");
	}
	
}
