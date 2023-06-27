package com.security.springmvc.controller;

import com.security.springmvc.model.AuthenticationRequest;
import com.security.springmvc.model.UserDto;
import com.security.springmvc.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/login",produces = "text/plain;charset=utf-8")
    public String login(AuthenticationRequest authenticationRequest, HttpSession session) {
        UserDto user = null;

        try {
            user = authenticationService.authentication(authenticationRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            // 把认证失败的msg反馈给前端
            return "认证失败,账号/密码错误";
        } else {
            //存入session
            session.setAttribute(UserDto.SESSION_USER_KEY, user);
            return user.getUsername() +"登录成功";
        }
    }

    @GetMapping(value = "/logout",produces = {"text/plain;charset=UTF-8"})
    public String logout(HttpSession session){
        //注销的时候,我们需要删除session,删除session 的同时,会自动删除cookie
        session.invalidate();
        return "退出成功";
    }

    @GetMapping(value = "/r/r1",produces = {"text/plain;charset=UTF-8"})
    public String r1(HttpSession session){
        String fullname = null;
        Object object = session.getAttribute(UserDto.SESSION_USER_KEY);
        if(object == null){
            fullname = "匿名";
        }else{
            UserDto userDto = (UserDto) object;
            fullname = userDto.getFullname();
        }
        return fullname+"访问资源r1";
    }

    @GetMapping(value = "/r/r2",produces = {"text/plain;charset=UTF-8"})
    public String r2(HttpSession session){
        String fullname = null;
        Object userObj = session.getAttribute(UserDto.SESSION_USER_KEY);
        if(userObj != null){
            fullname = ((UserDto)userObj).getFullname();
        }else{
            fullname = "匿名";
        }
        return fullname + " 访问资源2";
    }
}
