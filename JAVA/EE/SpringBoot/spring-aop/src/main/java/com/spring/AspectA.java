package com.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * 用于测试5个通知的执行顺序(可以debug观察): 前置通知,后置通知(@After),返回通知(@AfterReturning),异常通知
 *
 * 目标正常执行,执行过程中没有抛异常:
 *      进入环绕通知 => 前置通知 => 执行目标方法 => 退出环绕通知(正常退出环绕通知) => 后置通知(@After) => 返回通知
 * 目标方法执行过程中抛异常:
 *      进入环绕通知 => 前置通知 => 执行目标方法 => 退出环绕通知(抛出异常退出环绕通知) => 后置通知(@After) => 异常通知
 *
 * 不论目标方法正常执行 还是 抛出异常没有正常执行,都会执行后置通知(@After),表明目标方法执行结束了,管他正常结束还是异常结束,反正都要触发后置通知(@After)
 *
 * 可以把 返回通知 和 异常通知 统一归类为 最终通知.也就是最终肯定要有一个.二选一.
 */
@Aspect
@Order(1)
public class AspectA {

    @Pointcut("execution(* com.spring.ServiceA.*(..))")
    public void pointCut() {}

    @Before(value = "pointCut()")
    public void methodBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法 [" + methodName + "] 的<AspectA前置通知>,入参" + Arrays.asList(joinPoint.getArgs()));
    }

    // returning = "result": 用来告诉spring这个result是用来接受返回值的.参数名字要和returning的值一致.
    @AfterReturning(value = "pointCut()", returning = "result")
    public void methodReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法 [" + methodName + "] 的<AspectA返回通知>,入参" + Arrays.asList(joinPoint.getArgs()));
    }

    // throwing = "ex": 用来告诉spring用ex这个参数来接受异常.
    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void  methodAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法 [" + methodName + "] 的<AspectA异常通知>,入参" + Arrays.asList(joinPoint.getArgs()));
    }

    @After(value = "pointCut()")
    public void methodAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法 [" + methodName + "] 的<AspectA后置通知>,入参" + Arrays.asList(joinPoint.getArgs()));
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        System.out.println("AspectA进入环绕通知************");
        Object result = point.proceed(); // 调用目标方法,就是我们反射的 method.invoke();
        System.out.println("AspectA正常退出环绕通知************");
        return result;
    }
}
