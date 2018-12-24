package com.journaldev.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

// 我们可以使用Around aspect来cut the method execution before and after.

// 我们可以用它来控制: whether the advised method will execute or not。
// 我们还可以检查返回值并进行更改。
// 这是最强大的device，需要正确应用。This is the most powerful advice and needs to be applied properly.

@Aspect
public class EmployeeAroundAspect {

	// 1. Around advice始终要求使用ProceedingJoinPoint作为参数，
	// 2. 我们应该使用它的(ProceedingJoinPoint的) proceed（）方法来调用目标对象的advised method。 
	// 3. 如果advised方法返回某些内容，advice method将其返回给调用者程序。 
	// 4. 对于void方法，advice方法可以返回null。 由于around advice cut around the advised method, 
	// 我们可以控制方法的输入和输出以及它的执行行为。
	@Around("execution(* com.journaldev.spring.model.Employee.getName())")
	public Object employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
		System.out.println("Before invoking getName() method");
		Object value = null;
		try {
			value = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("After invoking getName() method. Return value="+value);
		return value;
	}
}
