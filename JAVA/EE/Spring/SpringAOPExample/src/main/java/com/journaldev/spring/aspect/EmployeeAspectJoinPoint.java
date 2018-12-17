package com.journaldev.spring.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

// We can use JoinPoint as parameter in the advice methods 
// and using it get the method signature or the target object.
// 我们可以使用JoinPoint作为 advice methods(通知方法)中的参数，
// 并使用它来获取方法签名或目标对象。


/**
 * We can use args() expression in the pointcut(切入点) to be applied 
 * to any method that matches the argument pattern. If we use this, 
 * then we need to use the same name in the advice method from where 
 * argument type is determined. We can use Generic objects also in 
 * the advice arguments.
 * 
 *   我们可以在 切入点 中使用 args（）表达式 来应用于 "与参数模式匹配" 的 任何方法。
 *   如果我们使用它，那么我们需要在确定参数类型的advice方法中使用相同的名称。 
 *   我们也可以在advice参数中使用Generic对象。
 */

@Aspect
public class EmployeeAspectJoinPoint {

	@Before("execution(public void com.journaldev.spring.model..set*(*))")
	public void loggingAdvice(JoinPoint joinPoint){
		System.out.println("Before running loggingAdvice on method="+joinPoint.toString());
		
		System.out.println("Agruments Passed=" + Arrays.toString(joinPoint.getArgs()));
	}
	
	//Advice arguments, will be applied to bean methods with single String argument
	@Before("args(name)")
	public void logStringArguments(String name){
		System.out.println("String argument passed="+name);
	}
	
}
