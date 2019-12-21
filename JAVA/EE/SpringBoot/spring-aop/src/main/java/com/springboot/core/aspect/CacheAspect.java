package com.springboot.core.aspect;

import com.springboot.core.aspect.annotation.CacheRedis;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

// 声明该类为一个切面类
@Aspect
@Component
// 只想在开发环境和测试环境中使用？
// 对于那些性能要求较高的应用，不想在生产环境中打印日志，只想在开发环境或者测试环境中使用，要怎么做呢？我们只需为切面添加 @Profile 就可以了，指定profile,这样就指定了只能作用于 dev 开发环境和 test 测试环境，生产环境 prod 是不生效的！
// @Profile({"dev", "test"})
public class CacheAspect {

    // 自定义注解的切入点,必须是注解的全路径.
    @Pointcut("@annotation(com.springboot.core.aspect.annotation.CacheRedis)")
    public void aa() {}

    // 执行顺序: around -> before -> 业务代码 -> around执行完毕 -> after -> afterReturing.
    @Around("aa()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        System.out.println("Around before 11111");
        Object result = proceedingJoinPoint.proceed();
        System.out.println("Around result: " + result);
        System.out.println("Around after 22222");
        System.out.println("Time consuming: " + (System.currentTimeMillis() - startTime) + "毫秒");
        return "around process " + result;
    }

    // 满足aa,并且带有注解cacheRedis
    @Before("aa() && @annotation(cacheRedis)")
    public void doBefore(JoinPoint joinPoint, CacheRedis cacheRedis) {
        System.out.println("before key: " + cacheRedis.key());
        System.out.println("before time: " + cacheRedis.expireTime());
    }

    @After("aa() && @annotation(cacheRedis)")
    public void doAfter(JoinPoint joinPoint, CacheRedis cacheRedis) {
        System.out.println("after key: " + cacheRedis.key());
        System.out.println("after time: " + cacheRedis.expireTime());
    }

    @AfterReturning(value = "aa() && @annotation(cacheRedis)", returning = "result")
    public void after(CacheRedis cacheRedis, Object result) {
        System.out.println("AfterReturning key: " + cacheRedis.key());
        System.out.println("AfterReturning time: " + cacheRedis.expireTime());
    }

    /**
     * 获取切面注解的描述
     * @param joinPoint 切点
     */
    public void getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(CacheRedis.class).key());
                    break;
                }
            }
        }
        System.out.println("--------" + description.toString());
    }

}
