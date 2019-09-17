package com.funtl.hello.spring.cloud.service.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打开 http://localhost:8761
 * 你会发现一个服务已经注册在服务中了，服务名为 HELLO-SPRING-CLOUD-SERVICE-ADMIN ,端口为 8762
 */
@RestController
public class AdminController {

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String sayHi(@RequestParam(value = "message")String message) {
        return String.format("Hi, your message is : %s i am from port : %s", message, port);
    }

}
