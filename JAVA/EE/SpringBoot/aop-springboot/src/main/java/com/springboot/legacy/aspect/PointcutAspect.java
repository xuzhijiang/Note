package com.springboot.legacy.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class PointcutAspect {

	// 我们在advice annotation参数中使用方法名称,而不是表达式，

    // 凡是使用getNamePointcut()作为advice参数的，都会匹配到"public String getName())"
    @Pointcut("execution(public String getName())")
    // 我们可以使用@Pointcut注释创建一个空方法，然后将其用作advices中的表达式。
    public void getNamePointcut(){}

    // Pointcut to execute on all the methods of classes in a package
    // 在com.springboot.legacy.bean包中的类的所有方法上执行.
    @Pointcut("within(com.springboot.legacy.other.*)")
    public void allMethodsPointcut(){
        // @Pointcut注解修饰的是空方法，里面的打印不会被执行
    }

	@Before("getNamePointcut()")
	public void loggingAdvice(){
		System.out.println("PointcutAspect loggingAdvice on getName()");
	}
	
	@Before("getNamePointcut()")
	public void secondAdvice(){
		System.out.println("PointcutAspect secondAdvice on getName()");
	}

    @Before("allMethodsPointcut()")
    public void allServiceMethodsAdvice(){
        System.out.println("PointcutAspect Before executing service method");
    }

}
