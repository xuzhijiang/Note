package com.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * 用于测试: 环绕通知
 */
@Aspect
public class AspectB {

    /**
     * 以注解作为切入点表达式
     */
    @Pointcut("@annotation(com.spring.CacheRedis)")
    public void myPointCut() {}

    /**
     * 注意,这里 @Around内的参数有2种写法:
     * 1). 如果@Around修饰的方法不需要 CacheRedis类型的参数,可以这么写: @Around("myPointCut()")
     * 2). 如果@Around修饰的方法需要 CacheRedis类型的参数,必须这么写: @Around("myPointCut() && @annotation(cacheRedis)")
     *      而且@annotation()中的值必须是doAround方法参数的名字
     */
    @Around("myPointCut() && @annotation(cacheRedis)")
    public Object doAround(ProceedingJoinPoint point, CacheRedis cacheRedis) throws Throwable {
        System.out.println("AspectB前置通知************");
        printCacheRedisInfo(cacheRedis);
        printAspect(point);
        Object result = point.proceed(); // 调用目标方法,就是我们反射的 method.invoke();
        return result;
    }

    private void printCacheRedisInfo(CacheRedis cacheRedis) {
        System.out.println("key: " + cacheRedis.key());
        System.out.println("expireTime: " + cacheRedis.expireTime());
    }

    private void printAspect(JoinPoint joinPoint) {
        // 获取目标类的类名
        String targetName = joinPoint.getTarget().getClass().getName();
        // 获取目标方法的方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取目标方法的参数列表
        Object[] arguments = joinPoint.getArgs();

        // 反射得到目标类的字节码
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 得到目标类的方法信息
        Method[] methods = targetClass.getMethods();
        StringBuilder sb = new StringBuilder();
        for (Method method : methods) {
            // 遍历目标类的所有方法,得到和目标方法同名的方法
            if (method.getName().equals(methodName)) {
                Class[] clazz = method.getParameterTypes();
                // 比较参数的长度是否一致,防止得到的是重载的方法.
                if (clazz.length == arguments.length) {
                    // 获取CacheRedis注解中的属性值
                    sb.append(method.getAnnotation(CacheRedis.class).key() + " : " + method.getAnnotation(CacheRedis.class).expireTime());
                    break;
                }
            }
        }
        System.out.println("***********************" + sb.toString());
    }
}
