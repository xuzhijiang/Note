package org.redis.distributed.core.intercept;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.redis.distributed.core.limit.RedisLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 当然使用时也得扫描到该包,让spring管理这个 bean
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CommonAspect {

    private static Logger logger = LoggerFactory.getLogger(CommonAspect.class);

    @Autowired
    private RedisLimit redisLimit;

    @Pointcut("@annotation(org.redis.distributed.core.annotation.CommonLimit)")
    private void check(){}

    /**
     * 在拦截过程中调用限流
     */
    @Before("check()")
    public void before(JoinPoint joinPoint) {
        if (redisLimit == null) {
            throw new NullPointerException("redisLimit is null");
        }
        boolean limit = redisLimit.limit();
        if (!limit) {
            // 调用达到阈值时抛出异常
            // 可使用专业的并发测试工具 JMeter测试
            logger.warn("request has been limited!!");
            throw new RuntimeException("request has been limited!!");
        }
    }
}
