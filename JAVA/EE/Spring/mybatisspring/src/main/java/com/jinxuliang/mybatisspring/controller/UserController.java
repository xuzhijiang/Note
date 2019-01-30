package com.jinxuliang.mybatisspring.controller;

import com.jinxuliang.mybatisspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 将Service对象注入到控制 器中，控制器使用它来执行 各种业务流程，
// 然后把处理 结果返回给客户端。这也是 一种典型的编程模式。
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/showUser/{id}")
    public String selectUser (@PathVariable int id){
          return userService.selectUser(id).toString();
    }


}

