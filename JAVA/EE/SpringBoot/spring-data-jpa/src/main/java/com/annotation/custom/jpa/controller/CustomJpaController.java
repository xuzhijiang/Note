package com.annotation.custom.jpa.controller;

import com.annotation.custom.jpa.domain.User;
import com.annotation.custom.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomJpaController {

    @Autowired
    private UserService userService;

    @GetMapping("/customJpa")
    public String testCustomJpa() {
        userService.add(new User("xzj", 1));
        return "success";
    }
}
