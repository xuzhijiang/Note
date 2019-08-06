package com.springboot.core.controller;

import com.springboot.core.bean.MyDataClass;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebRestController {

    @RequestMapping("/hello")
    public MyDataClass hello(){
        return MyDataClass.create();
    }

}
