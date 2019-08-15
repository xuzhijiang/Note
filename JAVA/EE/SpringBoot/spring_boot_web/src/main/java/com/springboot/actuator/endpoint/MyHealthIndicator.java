package com.springboot.actuator.endpoint;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 自定义健康端点（第1种方式）
 *
 * 实现HealthIndicator接口，根据自己的需要判断返回的状态是UP还是DOWN，功能简单。
 *
 * 简单测试: 访问 http://localhost:8080/actuator/health
 */
@Component("my1")
public class MyHealthIndicator implements HealthIndicator {

    private static final String VERSION = "v1.0.0";

    @Override
    public Health health() {
        int code = check();
        if (code != 0) {
            Health.down().withDetail("code", code).withDetail("version", VERSION).build();
        }
        return Health.up().withDetail("code", code)
                .withDetail("version", VERSION).up().build();
    }

    private int check() {
        return 0;
    }

}
