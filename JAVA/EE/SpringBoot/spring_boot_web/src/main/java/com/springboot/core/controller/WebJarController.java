package com.springboot.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebJarController {

    @RequestMapping("/webjar")
    public String countClickTime(Model model) {
        model.addAttribute("info", "Web Jar Test Info");
        return "webJar";
    }
}
