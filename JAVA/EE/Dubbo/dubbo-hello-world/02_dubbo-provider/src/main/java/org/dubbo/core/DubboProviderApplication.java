package org.dubbo.core;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 实现服务提供者 dubbo-provider
@SpringBootApplication
@EnableDubboConfiguration//开启dubbo的自动配置,注意使用的是@EnableDubboConfiguration，不是@EnableDubboConfig
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }

}
