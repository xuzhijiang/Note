package com.spring.mybatis.controller;

import com.spring.mybatis.domain.User;
import com.spring.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public String users(Map<String, Object> map) {
        List<User> users = userService.users();
        map.put("allUsers", users);
        return "list";
    }
}
