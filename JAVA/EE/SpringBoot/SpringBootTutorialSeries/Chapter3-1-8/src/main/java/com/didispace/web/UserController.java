package com.didispace.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 第三步：创建接收xml请求的接口,
 *
 * 完成了要转换的对象之后，可以编写一个接口来接收xml并返回xml
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