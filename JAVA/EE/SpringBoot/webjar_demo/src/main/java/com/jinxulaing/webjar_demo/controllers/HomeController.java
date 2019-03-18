package com.jinxulaing.webjar_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    //index方法的参数，Spring IoC容器会自动注入进来
    @RequestMapping("/")
    public String index(Model model){
        //向模板文件传送信息
        model.addAttribute("info","WebJar使用示例");
        //模板文件位置/resources/templates/home/index.html
        return "/home/index";
    }

}
