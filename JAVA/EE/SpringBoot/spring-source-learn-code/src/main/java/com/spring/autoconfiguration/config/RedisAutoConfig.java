package com.spring.autoconfiguration.config;

import com.spring.autoconfiguration.component.MyEnableAutoConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
// 可以把这个注解注释掉看效果
@MyEnableAutoConfig
public class RedisAutoConfig {
}
