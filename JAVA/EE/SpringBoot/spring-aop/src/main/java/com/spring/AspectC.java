package com.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

@Aspect
@Order(5)
public class AspectC {

    @Pointcut("execution(* com.spring.ServiceA.*(..)) || execution(* com.spring.ServiceA.*(..))")
    public void pointCut() {}

    @Before(value = "pointCut()")
    public void methodBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法 [" + methodName + "] 的<AspectC前置通知>,入参" + Arrays.asList(joinPoint.getArgs()));
    }

    // returning = "result": 用来告诉spring这个result是用来接受返回值的.参数名字要和returning的值一致.
    @AfterReturning(value = "pointCut()", returning = "result")
    public void methodReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法 [" + methodName + "] 的<AspectC返回通知>,入参" + Arrays.asList(joinPoint.getArgs()));
    }

    // throwing = "ex": 用来告诉spring用ex这个参数来接受异常.
    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void  methodAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法 [" + methodName + "] 的<AspectC异常通知>,入参" + Arrays.asList(joinPoint.getArgs()));
    }

    @After(value = "pointCut()")
    public void methodAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法 [" + methodName + "] 的<AspectC后置通知>,入参" + Arrays.asList(joinPoint.getArgs()));
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        System.out.println("AspectC进入环绕通知************");
        Object result = point.proceed(); // 调用目标方法,就是我们反射的 method.invoke();
        System.out.println("AspectC正常退出环绕通知************");
        return result;
    }
}
