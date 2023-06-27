package com.mybatis.core.SqlSessionTemplate;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// org.mybatis.spring.SqlSessionTemplate的作用演示
public class MainClass {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SqlSessionTemplate/applicationContext.xml");
        UserDao userDao = context.getBean(UserDao.class);
        User user = userDao.selectById(6);
        System.out.println(user);
    }
}
