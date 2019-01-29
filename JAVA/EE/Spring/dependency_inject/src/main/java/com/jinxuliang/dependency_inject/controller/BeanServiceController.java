package com.jinxuliang.dependency_inject.controller;

import com.jinxuliang.dependency_inject.service.IBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class BeanServiceController {
    @Autowired()
    @Qualifier("beanServiceA")
    IBeanService serviceA;

    @Autowired()
    @Qualifier("beanServiceB")
    IBeanService serviceB;

    //如果取消以下注释，则无法注入，因为实现IBeanService的有两个Bean
//    @Autowired
//    IBeanService service;


    @Override
    public String toString() {
        return "BeanServiceController{" +
                "serviceA=" + serviceA +
                ", serviceB=" + serviceB +
                '}';
    }
}
