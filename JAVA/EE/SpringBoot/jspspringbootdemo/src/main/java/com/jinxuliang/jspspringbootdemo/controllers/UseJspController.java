package com.jinxuliang.jspspringbootdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class UseJspController {

    @RequestMapping("/welcome")
    public ModelAndView welcome(){

        //如果没有在application.properties中定义了
        // spring.mvc.view.prefix和spring.mvc.view.suffix
        //则需要明确地指出其视图文件名
        //return new ModelAndView("/WEB-INF/jsp/welcome.jsp","now",new Date());

        //定义了suffix和prefix之后，可以简单地直接使用不包容路径和后缀的视图文件名
        return new ModelAndView("welcome","now",new Date());
    }
}

