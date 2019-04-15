package com.jinxuliang.mybatisspring.service;

import com.jinxuliang.mybatisspring.mapper.UserMapper;
import com.jinxuliang.mybatisspring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 定义Service

// 在实际开发中:
// 1. 我们通常会将要实现的业务逻辑功能抽象为Service接口和对象，
// 2. 然后，将MyBatis映射对象“注入”进来，在内部使用它来提取数据，
// 完成各种功能。这是一种典型的编程套路。

@Service
public class UserServiceImp implements UserService {

    //注入MyBatis映射对象
    @Autowired
    UserMapper userMapper;

    @Override
    public User selectUser(int id) {
        return userMapper.selectUserById(id);
    }
}