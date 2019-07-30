package com.springboot.legacy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AfterThrowingAspect {

    // 我们可以在切入点表达式中使用"within" 来应用于类中的所有方法。
    // 我们在Employee bean中有throwException(）方法来展示 "After Throwing device"的使用。
    // 当com.springboot.legacy.bean.Employee中的所有方法只要有一个抛出异常的时候,就调用此方法,如果没有方法抛出异常,就不会调用此方法.
    @AfterThrowing("within(com.springboot.legacy.bean.Employee)")
    public void logExceptions(JoinPoint joinPoint){
        System.out.println("Exception thrown in Employee Method="+joinPoint.toString());
    }

}
