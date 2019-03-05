package com.luo.springcloud.controller;

import com.luo.springcloud.entities.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptController_Consumer {

    // private static final String REST_URL_PREFIX = "http://localhost:8001";
    // 相比之前的http://localhost:8001，Ribbon和Eureka整合后，
    // Consumer可以直接通过服务名称来调用服务，而不再关心地址和端口号。

    private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";

    @Autowired
    private RestTemplate restTemplate;

    //http://localhost/consumer/dept/add?dname=AI
    @RequestMapping(value="/consumer/dept/add")
    public boolean add(Dept dept){
        return restTemplate.postForObject(REST_URL_PREFIX+"/dept/add",
                dept, Boolean.class);
    }

    //http://localhost/consumer/dept/get/2
    @RequestMapping(value = "/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/" + id,
                Dept.class);
    }

    //  http://localhost/consumer/dept/list
    @RequestMapping(value = "/consumer/dept/list")
    public List<Dept> list(){
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/list/", List.class);
    }

    // microservicecloud-consumer-dept-80调用microservicecloud-provider-dept-8001服务暴露在外的接口
    // // 测试@EnableDiscoveryClient,消费端可以调用服务发现
    // http://localhost/consumer/dept/discovery得到8001微服务信息
    @RequestMapping(value = "/consumer/dept/discovery")
    public Object discovery(){
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/discovery", Object.class);
    }
}
