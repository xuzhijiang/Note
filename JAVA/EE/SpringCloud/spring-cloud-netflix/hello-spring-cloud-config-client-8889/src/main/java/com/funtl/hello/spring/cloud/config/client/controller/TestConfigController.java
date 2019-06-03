package com.funtl.hello.spring.cloud.config.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConfigController {

    // 我们创建一个 Controller 来测试一下通过远程仓库的配置文件注入 foo 属性
    // 一般情况下，能够正常启动服务就说明注入是成功的。
    @Value("${foo}")
    private String foo;

    @Value("${demo.message}")
    private String demoMessage;

    /**
     * 测试访问
     * 浏览器端访问：http://localhost:8889/hi 显示如下：
     * foo version 1
     *
     * @return
     */
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hi() {
        return "foo: " + foo + ", demoMessage: " + demoMessage;
    }
}
