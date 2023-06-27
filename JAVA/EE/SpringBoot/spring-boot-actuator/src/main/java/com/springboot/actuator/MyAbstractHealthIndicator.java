package com.springboot.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * 自定义健康端点（第二种方式）,功能更加强大一点
 *
 * 继承AbstractHealthIndicator抽象类，重写doHealthCheck方法，
 * 功能比第一种要强大一点点
 *
 *  http://localhost:8080/actuator/health
 */
@Component("my2")
public class MyAbstractHealthIndicator extends AbstractHealthIndicator {

    private static final String VERSION = "v1.0.0";

    // 内容回调中还做了异常的处理
    // DataSourceHealthIndicator和RedisHealthIndicator类 都是这种写法,可以看源码
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int code = check();
        if (code != 0) {
            builder.down().withDetail("code", code).withDetail("version", VERSION).build();
        }
        builder.withDetail("code", code)
                .withDetail("version", VERSION).up().build();
    }

    private int check() {
        return 0;
    }

}
