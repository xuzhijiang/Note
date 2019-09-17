package com.funtl.hello.spring.cloud.web.admin.ribbon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    // 配置注入 RestTemplate 的 Bean，
    // 并通过 @LoadBalanced 注解表明我这个客户端要访问的是负载均衡服务(说明要访问的服务是集群).
    // 本例中就是在8762/8763这两个服务之间切换.
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
