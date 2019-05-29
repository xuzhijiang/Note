package com.funtl.hello.dubbo.service.user.provider.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funtl.hello.dubbo.service.user.api.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;

// 通过 @Service 注解实现服务提供方
@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    @Value("${spring.dubbo.protocol.port}")
    private String port;

    //在调用方法上增加 @HystrixCommand 配置，此时调用会经过 Hystrix 代理
//    @HystrixCommand(commandProperties = {
////            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
////            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
////    }) // 注意添加这个注解，不能连接zookeeper，原因未知.
    @Override
    public String sayHi() {
        // 测试熔断，所以注释，
         return "Hello Dubbo, i am from port : " + port;
        //throw new RuntimeException("Exception to show hystrix enabled.");
    }

    /**
     * 测试熔断器
     *
     * 此时我们再次请求服务提供者，浏览器会报 500 异常
     *
     * Exception to show hystrix enabled.
     */
}
