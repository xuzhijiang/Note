package com.di.controller;

import com.di.service.IBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class BeanServiceController {

    // 如果在注入时发现符合要求的Bean有多个，可以使用@Qualifier来人为指定选哪个Bean。
    @Autowired
    @Qualifier("beanServiceA")
    IBeanService serviceA;

    @Autowired
    @Qualifier("beanServiceB")
    IBeanService serviceB;

    // 下面这个无法实现依赖注入，因为IBeanService的实现类有两个Spring容器不知道要用哪个实现来注入
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
