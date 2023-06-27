package com.spring.order;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    // 定义横切点
    @Pointcut("execution(* com.spring.order.OrderController.*(..))")
    public void myPointCut() {}

    @Before(value = "myPointCut()")
    public void before() {
        System.out.println("before");
    }

    @Around(value = "myPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        Object result = joinPoint.proceed();
        System.out.println("around after");
        return result;
    }

    @After(value = "myPointCut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning(value = "myPointCut()")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing(value = "myPointCut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }
}
