package com.funtl.hello.spring.cloud.web.admin.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 在这里我们直接用的"程序名(spring.application.name)"替代了具体的 URL 地址，
 * 在 Ribbon 中它会根据"服务名"来选择具体的"服务实例"，
 * 然后根据“服务实例”在请求的时候会用具体的 URL 替换掉服务名，代码如下：
 */
@Service
public class AdminService {

    @Autowired
    private RestTemplate restTemplate;

    // 到时候发出请求的时候，会到eureka-server注册中心获取服务实例的列表,
    // 然后根据我们传的参数中的"服务的名称",访问某一个服务实例，可能是8762，或者可能是8763

    // Ribbon 中使用熔断器:
    // 在 Ribbon 调用方法上增加 @HystrixCommand 注解并指定 fallbackMethod 熔断方法
    @HystrixCommand(fallbackMethod = "hiError")
    public String sayHi(String message) {
        // 这里用了服务的名字,因为服务的ip和端口交给eureka来托管,服务也交给eureka来托管,所以能狗通过服务名字找到ip和端口
        return restTemplate.getForObject("http://HELLO-SPRING-CLOUD-SERVICE-ADMIN/hi?message=" + message, String.class);
    }

    public String hiError(String message) {
        return "Hi, your message is :\"" + message + "\" but request error.";
    }

}
