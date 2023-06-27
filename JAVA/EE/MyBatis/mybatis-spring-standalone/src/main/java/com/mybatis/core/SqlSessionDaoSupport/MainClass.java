package com.mybatis.core.SqlSessionDaoSupport;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// org.mybatis.spring.support.SqlSessionDaoSupport功能演示
public class MainClass {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SqlSessionDaoSupport/applicationContext.xml");
        UserDao userDao = context.getBean(UserDao.class);
        User user = userDao.selectById(6);
        System.out.println(user);
    }
}
