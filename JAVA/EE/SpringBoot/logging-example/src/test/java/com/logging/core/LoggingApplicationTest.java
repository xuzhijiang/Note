package com.logging.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggingApplicationTest {

    // spring boot采用的是slf4j+logback.
    // spring boot默认帮我们配置好了日志,我们只需要使用Logger打印即可
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void contextLoads() {
        // 日志的级别:
        // 由高到底: error>warn>info>debug>trace
        // 比如设置为info级别,就只会点>=info级别的日志
        logger.trace("trace level");
        logger.debug("debug level");
        // spring boot默认是info级别
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
    }
}
