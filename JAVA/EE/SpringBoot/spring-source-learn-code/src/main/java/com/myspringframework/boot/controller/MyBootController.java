package com.myspringframework.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MyBootController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/tuling")
    public String testTuling() {
        return "tuling";
    }

    @RequestMapping("/returnJson")
    public Object returnJson() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("name", "张三");
        return map;
    }

}
