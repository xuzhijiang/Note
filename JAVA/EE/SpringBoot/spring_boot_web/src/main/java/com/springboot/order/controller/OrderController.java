package com.springboot.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/home")
    public String home() {
        return "this is index page!!";
    }

}
