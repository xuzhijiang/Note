package org.dubbo.core;

import com.alibaba.dubbo.config.annotation.Reference;
import org.dubbo.core.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Reference
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello() {
        String hello = helloService.sayHello("xzj");
        return hello;
    }

}
