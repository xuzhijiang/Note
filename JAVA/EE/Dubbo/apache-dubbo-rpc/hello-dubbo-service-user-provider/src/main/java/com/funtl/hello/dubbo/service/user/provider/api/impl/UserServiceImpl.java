package com.funtl.hello.dubbo.service.user.provider.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funtl.hello.dubbo.service.user.api.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;

// 通过 dubbo的@Service 注解实现服务提供方
@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    @Value("${dubbo.protocol.port}")
    private String port;

    // Hystrix 实现服务熔断
    // 在调用方法上增加 @HystrixCommand 配置，此时调用会经过 Hystrix 代理
    // 当对特定的服务的调用(这里就是sayHi)的不可用达(指抛出异常等)到一个阀值（默认是Hystrix 是 5 秒 20 次） 熔断器将会被打开。
    // 这里是自定义为2秒10次就触发熔断.
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @Override
    public String sayHi() {
         //return "Hello Dubbo, i am from port : " + port;
         throw new RuntimeException("error!!");
    }

}
