package com.mybatis.core.legacy.steptwo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/steptwo/applicationContext.xml");
        UserService userService = new UserService();
        System.out.println(userService.selectById(context, 1));

        System.out.println(context.getBean("transactionManager"));
    }
}
