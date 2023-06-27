package com.shiro.spring.controller;

import com.shiro.spring.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shiro")
public class ShiroController {

    // 测试的时候要做logout,否则会用到shiro的缓存,不会有效果.
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "my-remember-me", defaultValue = "false") boolean rememberMe) {
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme(开发的时候要判断前端的记住我checkbox是否勾选了,)
            // 勾选了这里再设置为true.
            System.out.println("remember me: " + rememberMe);
            token.setRememberMe(true);
            try {
                System.out.println("************AAAA: " + token.hashCode());
                // 执行登录
                currentUser.login(token);
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类.
            catch (AuthenticationException e) {
                //unexpected condition?  error?
                System.out.println("登录失败: " + e.getMessage());
            }
        }

        return "redirect:/list.jsp";
    }

    @Autowired
    private ShiroService shiroService;

    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(HttpSession session){
        session.setAttribute("key", "value12345");
        shiroService.testMethod();
        return "redirect:/list.jsp";
    }
}
