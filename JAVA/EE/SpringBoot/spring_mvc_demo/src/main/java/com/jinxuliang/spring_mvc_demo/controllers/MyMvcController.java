package com.jinxuliang.spring_mvc_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc")
public class MyMvcController {

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("info","Hello,information from controller.");
        return "index";
    }
}
