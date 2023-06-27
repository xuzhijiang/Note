package com.qianfeng.controller;

import com.qianfeng.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login(){
        System.out.println("goto login page");
        return "login";
    }

    @PostMapping("/login")
    public String loginLogic(User user){
        System.out.println("login logic");
        //获取subject 调用login
        Subject subject = SecurityUtils.getSubject();
        // 创建用于登录的令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        // 登录失败会抛出异常，则交由异常解析器处理
        subject.login(token);
        return "index";// 登录成功，跳转首页
    }

    @GetMapping("/all")
    public String queryAllUsers(){
        System.out.println("query all users");
        return "index";
    }

    @RequestMapping("/perms/error")
    public String permsError(){
        System.out.println("权限不足");
        return "perm_error";
    }
}
