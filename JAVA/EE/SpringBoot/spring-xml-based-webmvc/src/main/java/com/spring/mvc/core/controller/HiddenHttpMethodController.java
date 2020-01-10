package com.spring.mvc.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HiddenHttpMethodController {
    @PutMapping("/restful/api/put")
    @ResponseBody // tomcat8需要加上这个,返回前端字符串
    public String put() {
        System.out.println("PUT*************");
        return "success_bad_code";
    }

    @DeleteMapping("/restful/api/delete")
    @ResponseBody
    public String delete() {
        System.out.println("DELETE*************");
        return "success_bad_code";
    }
}
