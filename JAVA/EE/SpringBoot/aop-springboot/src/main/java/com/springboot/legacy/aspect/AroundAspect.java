package com.springboot.legacy.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

// 我们可以用它来控制: whether the advised method will execute or not。
// 我们还可以检查返回值并进行更改。
// 这是最强大的device，需要正确应用
@Aspect
@Component
@EnableAspectJAutoProxy
public class AroundAspect {

	// 1. Around advice始终要求使用ProceedingJoinPoint作为参数，
	// 2. 我们应该使用它的(ProceedingJoinPoint的) proceed(）方法来调用目标对象的advised method。 
	// 3. 如果advised方法返回某些内容，advice method将其返回给调用者程序。 
	// 4. 对于void方法，advice方法可以返回null。
	// 5. 我们可以控制方法的输入和输出以及它的执行行为。
	@Around("execution(* com.springboot.legacy.bean.Employee.getName())")
	public Object employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
		System.out.println("Before invoking getName() method");
		Object value = null;
		try {
			value = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("After invoking getName() method. Return value="+value);
		return "around aspect process value-" + value;
	}
}
