package com.funtl.hello.spring.cloud.config.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConfigController {

    @Value("${server.port}")
    private String port;

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eurekaServer;

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hi() {
        return "port: " + port + ", eurekaServer: " + eurekaServer;
    }
}
