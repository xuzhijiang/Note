package org.mybatis.core.springboot.controller;

import org.mybatis.core.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/showUser/{id}")
    public String selectUser (@PathVariable int id){
          return userService.selectUser(id).toString();
    }

}