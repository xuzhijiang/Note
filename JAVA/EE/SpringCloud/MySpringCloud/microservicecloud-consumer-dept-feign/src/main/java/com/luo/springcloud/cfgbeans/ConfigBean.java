package com.luo.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ConfigBean {

    @Bean
    @LoadBalanced
    public RestTemplate geRestTemplate(){
        return new RestTemplate();
    }

    /**
     * Ribbon负载均衡算法使用方法,在客户端的配置类ConfigBean.java中添加IRule的实现
     */
    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
}
