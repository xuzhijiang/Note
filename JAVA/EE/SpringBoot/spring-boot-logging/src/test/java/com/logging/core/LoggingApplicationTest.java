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

    // spring boot默认帮我们配置好了日志,我们只需要使用Logger打印即可
    // spring boot默认采用的是slf4j+logback.
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void contextLoads() {
        // 日志的级别:
        // 由高到底: error>warn>info>debug>trace
        // 比如设置为info级别,就只会点>=info级别的日志
        logger.trace("trace level");
        logger.debug("debug level");
        //SpringBoot默认给使用的是info级别的，如果我们没有在配置文件中 指定打印级别,就用SpringBoot默认规定的级别
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
    }
}
