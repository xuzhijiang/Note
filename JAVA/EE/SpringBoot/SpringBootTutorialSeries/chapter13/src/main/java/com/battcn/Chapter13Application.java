package com.battcn;

import com.battcn.endpoint.MyEndPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 简单测试
 *
 * 启动项目，访问 http://localhost:8080/actuator/info 看到如下内容代表配置成功
 *
 * {
 *   "blog-url": "myblog.url,
 *   "author": "xzj",
 *   "version": "0.0.1-SNAPSHOT"
 * }
 */
@SpringBootApplication
public class Chapter13Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter13Application.class, args);
    }

    // 以为这就大功告成了吗，现实告诉我的是spring-boot默认是不认识这玩意的，
    // 得申明成一个Bean

    // 测试
    //完成准备事项后，启动Chapter13Application 访问
    // http://localhost:8080/actuator/xzj看到如下内容代表配置成功…

    //{
    //  "author": "xzj",
    //  "age": "26",
    //  "email": "2233@qq.com"
    //}
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