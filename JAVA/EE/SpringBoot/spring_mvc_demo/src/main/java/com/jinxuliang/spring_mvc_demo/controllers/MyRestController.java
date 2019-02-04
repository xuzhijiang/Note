package com.jinxuliang.spring_mvc_demo.controllers;

import com.jinxuliang.spring_mvc_demo.domain.MyDataClass;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyRestController {
    @RequestMapping("/hello")
    public MyDataClass hello(){
        return MyDataClass.create();
    }
}
