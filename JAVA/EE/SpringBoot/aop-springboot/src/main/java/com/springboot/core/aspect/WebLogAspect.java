package com.springboot.core.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *  实现Web层的日志切面,实现AOP的切面主要有以下几个要素：
 *
 * 1. 使用@Aspect注解将一个java类定义为切面类
 * 2. 使用@Pointcut定义一个切入点，可以是一个规则表达式，
 * 比如下例中某个package下的所有函数，也可以是一个注解等。
 * 3. 根据需要在切入点不同位置的切入内容
 * 4. 使用@Before在切入点开始处切入内容
 * 5. 使用@After在切入点结尾处切入内容
 * 6. 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
 * 7. 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
 * 8. 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private Logger logger = Logger.getLogger(getClass());

    /**
     * 优化解决AOP切面中的同步问题:
     *
     * 在WebLogAspect切面中，分别通过doBefore和doAfterReturning两个独立函数实现
     * 了切点头部和切点返回后执行的内容，若我们想统计请求的处理时间，
     * 就需要在doBefore处记录时间，并在doAfterReturning处通过当前时间与开始处记录的时间
     * 计算得到请求处理的消耗时间。
     *
     * 那么我们是否可以在WebLogAspect切面中定义一个成员变量来
     * 给doBefore和doAfterReturning一起访问呢？是否会有同步问题呢？的确，直接在这里定义基本类型会有同步问题，所以我们可以引入ThreadLocal对象，像下面这样进行记录：
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    // 通过@Pointcut定义的切入点为com.springboot.core包下的所有函数
    @Pointcut("execution(public * com.springboot.core..*.*(..))")
    public void webLog(){}

    @Before("webLog()") //通过@Before实现，对请求内容的日志记录
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    // 通过@AfterReturning记录请求返回的对象。
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) + "毫秒");
    }


}

