package com.springboot.core.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *  实现Web层的日志切面功能
 *
 *  实现AOP的切面主要有以下几个要素：
 *
 * 1. 使用@Aspect注解将一个java类定义为切面类
 * 2. 使用@Pointcut定义一个切入点，可以是一个规则表达式，也可以是一个注解等
 *
 * 然后根据需要在切入点不同位置的切入内容
 *
 * AOP Advice Type(Advice的执行策略):
 *
 * 4. @Before在切点之前，植入相关代码
 * 5. 使用@After在切点之后，植入相关代码
 * 6. 使用@AfterReturning:在切点返回内容后，植入相关代码，一般用于对返回值做些加工处理的场景；
 * 7. 使用@Around: 可以在切入点前后植入代码，并且可以自由的控制何时执行切点；
 * 8. @AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
 *
 * 执行顺序见图: Aop切入执行顺序.jpg
 */
@Aspect // @Aspect注解表示这是一个该类是一个切面类
// 多切面如何指定优先级？假设说我们的服务中不止定义了一个切面，比如说我们针对 Web 层的接口，不止要打印日志，还要校验 token 等。要如何指定切面的优先级呢？也就是如何指定切面的执行顺序？
//我们可以通过 @Order(i)注解来指定优先级，注意：i 值越小，优先级则越高。
// 注意是FILO模型,在切点之前，@Order 从小到大被执行，也就是说越小的优先级越高；
//在切点之后，@Order 从大到小被执行，也就是说越大的优先级越高；
@Order(5)
@Component// @Component注解表示将该类交于Spring容器来管理，
public class WebLogAspect {

    private Logger logger = Logger.getLogger(getClass());

    /**
     * 优化解决AOP切面中的同步问题:
     *
     * 通过doBefore和doAfterReturning两个独立函数实现了切人点头部和切入点返回后执行的内容，
     * 若我们想统计请求的处理时间，就需要在doBefore处记录时间，并在doAfterReturning处计算得到请求处理的消耗时间。
     *
     * 那么我们是否可以在WebLogAspect切面中定义一个成员变量来给doBefore和doAfterReturning一起访问呢？
     * 是否会有同步问题呢？的确，直接在这里定义基本类型会有同步问题，所以我们可以引入ThreadLocal对象
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    // 通过@Pointcut定义的切入点为com.springboot.core包下的所有函数
    @Pointcut("execution(public * com.springboot.core..*.*(..))")
    public void webLog(){}
    // 定义一个类的所有方法
    // @Pointcut("execution(public * com.springboot.core.controller.CoreController.*(..))")

    // 通过@Before实现，对请求的内容进行日志记录
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 找到http请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    // 通过@AfterReturning记录计算请求处理的消耗时间
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) + "毫秒");
    }

}