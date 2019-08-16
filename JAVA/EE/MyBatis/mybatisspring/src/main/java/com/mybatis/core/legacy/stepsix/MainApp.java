package com.mybatis.core.legacy.stepsix;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 整合十：多数据源
 */
public class MainApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/stepsix/applicationContext.xml");
        UserDao userDao = context.getBean(UserDao.class);
        User user = userDao.selectById(1);
        System.out.println(user);

        AccountDao accountDao = context.getBean(AccountDao.class);
        System.out.println(accountDao.selectById(11));
    }
}
