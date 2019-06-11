package org.redis.distributed.core.intercept;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CommonAspect {

    private static Logger logger = LoggerFactory.getLogger(CommonAspect.class);

//    private RedisLimi
}
