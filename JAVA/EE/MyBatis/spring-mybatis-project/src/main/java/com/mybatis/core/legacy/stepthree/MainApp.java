package com.mybatis.core.legacy.stepthree;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/stepthree/applicationContext.xml");
        UserDao userDao = context.getBean(UserDao.class);
        User user = userDao.selectById(1);
        System.out.println(user);
    }
}
