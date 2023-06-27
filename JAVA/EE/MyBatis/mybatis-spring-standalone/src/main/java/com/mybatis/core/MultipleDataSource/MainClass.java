package com.mybatis.core.MultipleDataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// 多数据源
public class MainClass {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("MultipleDataSource/applicationContext.xml");
        UserDao userDao = context.getBean(UserDao.class);
        User user = userDao.selectById(6);
        System.out.println(user);

        AccountDao accountDao = context.getBean(AccountDao.class);
        System.out.println(accountDao.selectById(11));
    }
}
