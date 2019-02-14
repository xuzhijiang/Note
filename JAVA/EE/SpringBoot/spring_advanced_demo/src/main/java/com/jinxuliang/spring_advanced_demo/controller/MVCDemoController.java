package com.jinxuliang.spring_advanced_demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jinxuliang.spring_advanced_demo.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

//展示Spring Boot项目中MVC控制器的基本功能
@Controller
@RequestMapping("/mvc")
public class MVCDemoController {

    Random ran = new Random();

    //获取一个指定id的User对象
    User getUser(int id) {
        User user = new User();
        user.setUsername("user" + ran.nextInt(100));
        user.setId(id);
        user.setAge(ran.nextInt(120));
        user.setGender(id % 2 == 0 ? "男" : "女");
        user.setPassword(ran.nextLong() + "");
        return user;
    }

    //localhost:8080/mvc/user/100
    //展示用户的简单信息
    @GetMapping("/user/{id:\\d+}")
    @JsonView(User.UserSimpleView.class)//在控制器中指定要使用的JsonView
    @ResponseBody
    public User getSimpleInfo(@PathVariable int id) {
        return getUser(id);
    }

    //localhost:8080/mvc/user/100/detail
    //展示用户的详细信息
    @GetMapping("/user/{id:\\d+}/detail")
    @JsonView(User.UserDetailView.class)
    @ResponseBody
    public User getDetailInfo(@PathVariable int id) {
        return getUser(id);
    }

    // 测试全局异常处理器:在MVCDemoController的这个方法中主动抛出一个异常进行测试
    // access: localhost:8080/mvc/advice
    //展示使用ControllerAdivce所定义的全局异常处理器的使用
    @RequestMapping("/advice")
    public String useControllerAdvice() {
        throw new IllegalArgumentException("参数有误！");
    }
    //控制器的方法中出现了未捕获的异常，就会由@ControllerAdivice配
    // 置的全局异常处理器进行处理，显示一个异常处理页。
}
