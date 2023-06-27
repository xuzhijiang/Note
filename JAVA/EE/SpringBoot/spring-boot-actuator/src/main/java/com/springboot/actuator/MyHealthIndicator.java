package com.springboot.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 自定义健康状态指示器 （第1种方式）
 * 1、编写一个指示器 实现 HealthIndicator 接口
 * 2、指示器的名字 xxxxHealthIndicator(spring-boot-starter-actuator下有很多自带的,
 * 比如RedisHealthIndicator,会在配置了redis的情况下生效)
 * 3、加入容器中
 * 4. 访问 http://localhost:8080/actuator/health
 */
@Component
public class MyHealthIndicator implements HealthIndicator {

    // 根据自己的需要判断是否健康,然后返回健康状态(返回UP还是DOWN)
    @Override
    public Health health() {
        int status = check();
        if (status != 0) { // 如果不健康
            return Health.down().withDetail("code", "服务异常").withDetail("version", "v1.0.0").build();
        } else { // 如果健康, Health.up().build()代表健康
            return Health.up().withDetail("code", status).withDetail("version", "v1.0.0").up().build();
        }
    }

    // 自定义检查方法
    private int check() {
        return 0;
    }
}
