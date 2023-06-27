package com.spring.session.redis.core.controller;

import com.spring.session.redis.core.common.BaseResponse;
import com.spring.session.redis.core.bean.User;
import com.spring.session.redis.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    @ResponseBody
    public String index(){
        return "首页!!!";
    }

    @GetMapping("/login")
    public String login(){
        return "test-login.html";
    }

}
