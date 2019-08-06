package com.springboot.core.controller;

import com.springboot.core.bean.User;
import com.springboot.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CoreController {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping(path = "/insert", method = RequestMethod.GET)
    public String insertUser(){
        User user = new User();
        user.setName("xzj");
        userMapper.insert(user);

        int id = user.getId();// 获得自动生成的主键
        User u2 = new User();
        u2.setName("xxxxx");
        u2.setId(id);
        userMapper.update(u2);

        return u2.toString();
    }

}
