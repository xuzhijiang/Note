package org.dubbo.core.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.dubbo.core.service.HelloService;
import org.springframework.stereotype.Component;

// dubbo-provider：实现公共接口并”提供服务“的“服务提供方所在模块“
@Component
@Service//@Service 注解使用的是 Dubbo 提供的，而不是 Spring 提供的
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}