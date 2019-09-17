package com.funtl.hello.spring.cloud.web.admin.ribbon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// 同时Ribbon 也是是一个负载均衡客户端.
// 在ribbon中使用负载均衡如下,需要在配置RestTemplate的时候使用@LoadBalanced
// 注意: 稍后使用的feign也是一个负载均衡客户端(服务消费者),默认帮我们集成了负载均衡功能.
@Configuration
public class RestTemplateConfiguration {

    // 配置RestTemplate
    // 并通过 @LoadBalanced 注解表明我这个客户端要访问的是负载均衡服务(说明我们要访问的服务是集群).
    // 本例中就是在8762/8763这两个服务之间切换.
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
