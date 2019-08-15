package com.springboot.xmlSupport.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 第三步：创建接收xml请求的接口,
 */
@Controller
public class UserController {

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public User create(@RequestBody User user) {
        user.setName("xzj.com: " + user.getName());
        user.setAge(user.getAge() + 100);
        return user;
    }

}