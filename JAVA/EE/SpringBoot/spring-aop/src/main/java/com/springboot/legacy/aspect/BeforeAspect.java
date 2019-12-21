package com.springboot.legacy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect// 切面类要求有@Aspect注解
@Component
@EnableAspectJAutoProxy
public class BeforeAspect {

	// @Before annotation中传递的字符串参数是Pointcut表达式(横切点表达式)
	// 4. 如果我们使用new运算符创建Employee other，然后调用Employee的getName()则不会应用advice。
	// 5. 只有当我们使用ApplicationContext来获取bean时，然后调用Employee bean的getName(),才会应用advice。
	@Before("execution(public String getName())")
	public void getName(){
		System.out.println("BeforeAspect Executing Advice on getName()");
	}

	// asterisk: 星号
	 // 7. 我们可以在Pointcut表达式中使用asterisk(*)作为通配符(wild card)，
	 // 将应用于com.journaldev.spring.service包中的所有类的其名称以get开头并且不带任何参数的method
	@Before("execution(* com.springboot.legacy.service.*.get*())")
	public void get(){
		System.out.println("Service method getter called");
	}

	@Before("execution(public void com.springboot.legacy.other..set*(*))")
	public void loggingAdvice(JoinPoint joinPoint){
		System.out.println("BeforeAspect Before: " + Arrays.toString(joinPoint.getArgs()));
	}

	// args()表达式来应用于method中参数个数,参数类型匹配的方法
	// 将会应用于只有一个String参数的method
	@Before("args(name)")
	public void logStringArguments(String name){
		System.out.println("BeforeAspect String argument passed="+name);
	}
}
