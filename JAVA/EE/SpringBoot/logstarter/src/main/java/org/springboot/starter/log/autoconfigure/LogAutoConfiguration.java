package org.springboot.starter.log.autoconfigure;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.starter.log.annotation.Log;
import org.springboot.starter.log.aop.LogMethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
//启动指定类的ConfigurationProperties功能,
// 也就是使用LogProperties.class的配置
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration extends AbstractPointcutAdvisor {

    private Logger logger = LoggerFactory.getLogger(LogAutoConfiguration.class);

    private Pointcut pointcut;

    // advice指的是在pointcut之前(Before),还是之后(After),还是返回值以后(AfterReturning)
    private Advice advice;

    @Autowired
    private LogProperties logProperties;

    @PostConstruct
    public void init(){
        logger.info("init LogAutoConfiguration start 111");
        // 由于计算方法耗时需要使用aop相关的lib，所以我们的AutoConfiguration继承了AbstractPointcutAdvisor。
        // 这样就有了Pointcut和Advice。Pointcut是一个支持"注解Log修饰方法"的Pointcut，Advice则自己实现：
        this.pointcut = new AnnotationMatchingPointcut(null, Log.class);
        this.advice = new LogMethodInterceptor(logProperties.getExcludeArr());
        logger.info("init LogAutoConfiguration end 111");
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

}
