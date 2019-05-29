package com.funtl.hello.dubbo.service.user.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funtl.hello.dubbo.service.user.api.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试负载均衡
 *
 * 修改端口号并分别启动服务提供者，此时访问服务消费者：http://localhost:8081/hi
 *
 * 浏览器会交替显示：
 *
 * Hello Dubbo , i am from port:20880
 * Hello Dubbo , i am from port:20881
 */
@RestController
public class UserController {

    @Reference(version = "${user.service.version}")
    private UserService userService;

    // http://localhost:8081/hi
    @HystrixCommand(fallbackMethod = "hiError") //在调用方法上增加 @HystrixCommand 注解，并指定 fallbackMethod 方法
    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String sayHi() {
        return userService.sayHi();
    }

    /**
     * 测试熔断器
     *
     * 此时我们再次请求服务提供者，http://localhost:8081/hi,浏览器会显示：
     *
     * Hystrix fallback
     */
    public String hiError() {
        return "Hystrix fallback method";
    }
}
