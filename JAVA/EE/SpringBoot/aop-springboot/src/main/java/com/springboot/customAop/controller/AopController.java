package com.springboot.customAop.controller;

import com.springboot.customAop.factory.BeanFactory;
import com.springboot.customAop.service.UserService;
import com.springboot.customAop.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {

    // 开启事务后，Controller并不是直接调用我们自己写的Service，而是Spring提供的代理对象
    private UserService userService;

    @GetMapping("/aop")
    public void aop() {
        BeanFactory beanFactory = new BeanFactory();
        try {
            // 这个userService一定是我们写的UserServiceImpl的实例吗？
            // Spring依赖注入的对象并不一定是我们自己写的类的实例，也可能是userServiceImpl的代理对象。
            // 如果在UserService上使用@Transactional注解,那么就是代理对象,因为要增强功能,否则就是UserServiceImpl对象
            userService = (UserService) beanFactory.getBean(UserServiceImpl.class);
            System.out.println(userService.getClass().getName());
            userService.getUser();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
