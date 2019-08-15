package com.itranswarp.springcloud.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 访问http://localhost:8080/，可以正常访问。
 * 但是访问http://localhost:8080/hello的时候被重定向到了http://localhost:8080/login页面，
 * 因为没有登录，用户没有访问权限，通过输入用户名user和密码password进行登录后，
 * 跳转到了Hello World页面，再也通过访问http://localhost:8080/login?logout，就可以完成注销操作。
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @GetMapping("/api")
    @ResponseBody
    public String[] api() {
        return "Spring Boot Security".split(" ");
    }
}