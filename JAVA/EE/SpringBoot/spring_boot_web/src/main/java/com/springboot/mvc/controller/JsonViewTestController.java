package com.springboot.mvc.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.springboot.mvc.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
@RequestMapping("/jsonView")
public class JsonViewTestController {

    User getUser(int id) {
        User user = new User();
        user.setUsername("user" + new Random().nextInt(100));
        user.setId(id);
        user.setAge(new Random().nextInt(120));
        user.setGender(id % 2 == 0 ? "男" : "女");
        user.setPassword(new Random().nextLong() + "");
        return user;
    }

    @GetMapping("/user/{id:\\d+}")
    @JsonView(User.UserSimpleView.class)// 在控制器中指定要使用的JsonView
    @ResponseBody
    public User getSimpleInfo(@PathVariable int id) {
        return getUser(id);
    }

    @GetMapping("/user/{id:\\d+}/detail")
    @JsonView(User.UserDetailView.class)
    @ResponseBody
    public User getDetailInfo(@PathVariable int id) {
        return getUser(id);
    }

}
