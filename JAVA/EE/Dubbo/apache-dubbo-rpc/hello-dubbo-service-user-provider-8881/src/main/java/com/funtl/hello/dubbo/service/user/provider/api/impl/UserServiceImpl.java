package com.funtl.hello.dubbo.service.user.provider.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.funtl.hello.dubbo.service.user.api.UserService;
import org.springframework.beans.factory.annotation.Value;

// 通过 @Service 注解实现服务提供方
@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    @Value("${spring.dubbo.protocol.port}")
    private String port;

    @Override
    public String sayHi() {
        return "Hello Dubbo, i am from port : " + port;
    }

}
