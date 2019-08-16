package com.mybatis.core.legacy.steptwo;

import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

public class UserService {

    @Transactional
    public User selectById(ApplicationContext context, int id) {
        UserDao userDao = context.getBean(UserDao.class);
        User user = userDao.selectById(1);
        System.out.println(user);
        return user;
    }
}
