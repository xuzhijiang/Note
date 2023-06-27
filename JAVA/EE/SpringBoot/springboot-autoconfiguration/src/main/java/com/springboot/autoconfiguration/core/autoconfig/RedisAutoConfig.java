package com.springboot.autoconfiguration.core.autoconfig;

import com.springboot.autoconfiguration.core.autoconfig.MyEnableAutoConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
// 可以把这个注解注释掉看效果
@MyEnableAutoConfig
public class RedisAutoConfig {
}
