package com.springboot.customAop.service.impl;

import com.springboot.customAop.annotation.MyTransactional;
import com.springboot.customAop.service.UserService;

// 给UserServiceImpl添加@MyTransactional注解，得到代理对象：
@MyTransactional
public class UserServiceImpl implements UserService {

    @Override
    public void getUser() {
        System.out.println("service 执行");
    }

}
