package com.luo.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ConfigBean {

    /**
     * RestTemplate提供了多种便捷访问远程Http服务的方法，
     * 是一种简单高效便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具类集，
     * 使用方法如下
     *
     * （url,requestMap,ResponseBean.class）三个参数分别代表Rest请求地址，请求参数，
     * HTTP响应转换被转换的对象类型
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate geRestTemplate(){
        //客户端使用restTemplate访问服务端中的数据接口
        return new RestTemplate();
    }

    /**
     * Ribbon负载均衡算法使用方法
     *
     * 在客户端的配置类ConfigBean.java中添加IRule的实现
     * @return
     */
    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
}
