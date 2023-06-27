package com.springboot.web.restful.curd.controller;

import com.springboot.web.restful.curd.exception.UserNotExistException;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "username", defaultValue = "xxx") String username) {
        if ("aaa".equals(username)) {
            throw new UserNotExistException();
        }
        return "hello " + username;
    }

    // 查出用户数据，在页面展示
    @GetMapping("/success")
    public String success(Map<String, Object> map) {
        map.put("hello", "<h1>你好</h1>");
        map.put("users", Arrays.asList("zhangsan", "lisi", "wangwu"));
        return "success";
    }

    /**
     * HttpEntity比较强大.
     */
    @GetMapping("/httpEntity")
    public String httpEntity(HttpEntity<String> str) {
        System.out.println("***********str: " + str);
        return "success";
    }
}
