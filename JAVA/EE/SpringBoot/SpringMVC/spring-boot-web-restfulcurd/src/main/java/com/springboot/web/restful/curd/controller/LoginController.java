package com.springboot.web.restful.curd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    // @DeleteMapping
    // @PutMapping
    // @GetMapping
    //@RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username, // 使用@RequestParam明确的告诉它从请求参数中获取要获取的参数名字叫username
                        @RequestParam("password") String password,
                        HttpSession session,
                        Map<String, Object> map) {
        if (!StringUtils.isEmpty(username) && "123456".equals(password)) { // 登陆成功
            session.setAttribute("loginUser", username);
            // 为了防止表单重复提交，可以重定向到主页
            return "redirect:/main.html";
        } else { // 登录失败的处理
            map.put("msg", "用户名或密码错误,请重新输入!!");
            return "login";
        }
    }

}
