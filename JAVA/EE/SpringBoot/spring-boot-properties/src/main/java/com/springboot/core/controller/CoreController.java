package com.springboot.core.controller;

import com.springboot.core.domain.*;
import com.springboot.core.config.ELConfig;
import com.springboot.core.profile.IProfileBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class CoreController implements ApplicationContextAware {

    @Autowired
    UrlFetcher urlFetcher;

    @Autowired
    ApplicationContext ioc;

    @Autowired
    Person1 person1;

    @Autowired
    Person2 person2;

    @Autowired
    Person3 person3;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ioc = applicationContext;
    }

    @Value("${person.lastName}")
    private String name;

    @RequestMapping("/sayHello")
    public String sayHello(){
        return "Hello "+name;
    }

    @RequestMapping(path = "/person1", method = RequestMethod.GET)
    public Object person1(){
        System.out.println("person1: " + person1);
        return person1;
    }

    @RequestMapping(path = "/person2", method = RequestMethod.GET)
    public Object person2(){
        System.out.println("person2: " + person2);
        return person2;
    }

    @RequestMapping(path = "/person3", method = RequestMethod.GET)
    public Object person3(){
        System.out.println("person3: " + person3);
        return person3;
    }

    @GetMapping(value = "/testProfile")
    private void testProfile() {
        //依据spring.profiles.active的值，实例化不同的Bean
        IProfileBean profileBean = ioc.getBean(IProfileBean.class);
        System.out.println(profileBean);
    }

    @GetMapping(value = "/testSpringEL")
    private void testSpringEL() throws IOException {
        //演示Spring表达式
        ELConfig config = ioc.getBean(ELConfig.class);
        config.printFields();
    }

    @GetMapping("/testEnvironment")
    private void testEnvironment() {
        EnvironmentBean bean = ioc.getBean(EnvironmentBean.class);
        System.out.println(bean.getName());
    }
}
