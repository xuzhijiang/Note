package org.mybatis.core.springboot.service.impl;

import org.mybatis.core.springboot.mapper.UserMapper;
import org.mybatis.core.springboot.model.User;
import org.mybatis.core.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User selectUser(int id) {
        return userMapper.selectUserById(id);
    }

}
