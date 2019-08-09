package com.springboot.order.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    @Before("execution(* com.springboot.order.controller.OrderController.*(..))")
    public void before() {
        System.out.println("before");
    }

    @After("execution(* com.springboot.order.controller.OrderController.*(..))")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("execution(* com.springboot.order.controller.OrderController.*(..))")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing("execution(* com.springboot.order.controller.OrderController.*(..))")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("execution(* com.springboot.order.controller.OrderController.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        Object result = joinPoint.proceed();
        System.out.println("around after");
        return result;
    }
}
