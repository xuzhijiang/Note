package com.springboot.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 启动项目，访问 http://localhost:8080/actuator/info (info端点)
 *
 * {
 *   "blog-url": "myblog.url,
 *   "author": "xzj",
 *   "version": "0.0.1-SNAPSHOT"
 * }
 */
@SpringBootApplication
public class ActuatorApplicaiton {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplicaiton.class, args);
    }

    /**
     * 访问 http://localhost:8080/actuator/xzj
     */
    @Configuration
    static class MyEndpointConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnEnabledEndpoint
        public MyEndPoint myEndPoint() {
            return new MyEndPoint();
        }
    }

}