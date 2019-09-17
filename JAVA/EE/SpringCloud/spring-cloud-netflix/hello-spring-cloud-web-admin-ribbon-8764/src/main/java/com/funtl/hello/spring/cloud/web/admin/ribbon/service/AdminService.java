package com.funtl.hello.spring.cloud.web.admin.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminService {

    @Autowired
    private RestTemplate restTemplate;

    // Ribbon 中使用熔断器:
    // 在 Ribbon 调用方法上增加 @HystrixCommand 注解并指定 fallbackMethod 熔断方法
    @HystrixCommand(fallbackMethod = "hiError")
    public String sayHi(String message) {
        // 这里用了服务的名字,因为服务提供者(8762/8763)的ip和端口交给eureka Server来托管,
        // 服务也交给eureka server来托管,所以能够通过服务名字找到ip和端口
        // 到时候发出请求的时候，会到eureka-server注册中心获取服务实例的列表,
        // 然后根据我们传的参数中的"服务的名称",访问某一个服务实例，可能是8762，或者可能是8763
        return restTemplate.getForObject("http://HELLO-SPRING-CLOUD-SERVICE-ADMIN/hi?message=" + message, String.class);
    }

    /**
     * 测试熔断器:
     * 此时我们关闭服务提供者8762/8763，再次请求 http://localhost:8764/hi?message=HelloRibbon 浏览器会显示：
     * Hi，your message is :"HelloRibbon" but request error.
     * */
    public String hiError(String message) {
        return "Hi, your message is :\"" + message + "\" but request error (Ribbon Error).";
    }

}
