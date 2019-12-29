package com.springboot.tuling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
// 添加这个注解就可以实现aop的功能,注解版的aop 替代了 xml版的aop配置
@EnableAspectJAutoProxy
// @EnableAspectJAutoProxy(proxyTargetClass = true) // true: 强制使用cblib代理
public class MainConfig {
    @Bean
    public Calculate calculate() {
        return new TulingCalculate();
    }

    @Bean
    public TulingLogAspect tulingLogAspect() {
        return new TulingLogAspect();
    }
}
