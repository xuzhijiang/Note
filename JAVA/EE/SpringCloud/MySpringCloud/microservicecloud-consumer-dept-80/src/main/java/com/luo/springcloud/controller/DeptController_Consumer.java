package com.luo.springcloud.controller;

import com.luo.springcloud.entities.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// microservicecloud-consumer-dept-80调用microservicecloud-provider-dept-8001服务暴露在外的接口

// RestTemplate类是Spring用于构建Restful服务而提供的一种Rest服务客户端，RestTemplate提供了多种便捷访问远程Http服务的方法，能够大大提高客户端的编写效率，所以很多客户端比如 Android或者第三方服务商都是使用 RestTemplate 请求 restful 服务。
// 通过RestTemplate来访问各个微服务提供的接口
@RestController
@RequestMapping("/consumer")
public class DeptController_Consumer {

    // private static final String REST_URL_PREFIX = "http://localhost:8001";
    // 相比之前的http://localhost:8001，Ribbon和Eureka整合后，
    // Consumer可以直接通过服务名称来调用服务(服务名称即:microservicecloud-provider-dept-8001中yml中的spring.application.name)，
    // 而不再关心地址和端口号。

    // 这里的这个REST_URL_PREFIX是microservicecloud-provider-dept-8001中yml中的spring.application.name
    private static final String REST_URL_PREFIX = "http://my-microservicecloud-dept-8001";

    @Autowired
    private RestTemplate restTemplate;

    //http://localhost/consumer/dept/add?dname=AI
    @RequestMapping(value="/dept/add")
    public boolean add(Dept dept){
        return restTemplate.postForObject(REST_URL_PREFIX+"/dept/add",
                dept, Boolean.class);
    }

    //http://localhost/consumer/dept/get/2
    @RequestMapping(value = "/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/" + id,
                Dept.class);
    }

    //  http://localhost/consumer/dept/list
    @RequestMapping(value = "/dept/list")
    public List<Dept> list(){
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/list/", List.class);
    }

    // 测试@EnableDiscoveryClient,消费端可以调用服务发现
    // http://localhost/consumer/dept/discovery得到8001微服务信息
    @RequestMapping(value = "/dept/discovery")
    public Object discovery(){
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/discovery", Object.class);
    }
}
