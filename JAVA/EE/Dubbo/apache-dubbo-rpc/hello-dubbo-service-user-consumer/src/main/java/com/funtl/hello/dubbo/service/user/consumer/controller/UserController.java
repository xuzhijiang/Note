package com.funtl.hello.dubbo.service.user.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funtl.hello.dubbo.service.user.api.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // 通过 dubbo的@Reference 注入 UserService
    @Reference(version = "${user.service.version}")
    private UserService userService;

    //在调用方法上增加 @HystrixCommand 注解，并指定 fallbackMethod 方法
    @HystrixCommand(fallbackMethod = "hiError")
    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String sayHi() {
        return userService.sayHi();
    }

    public String hiError() {
        return "Hystrix fallback method";
    }
}
