package com.jinxuliang.mybatisspring.service;

import com.jinxuliang.mybatisspring.mapper.UserMapper;
import com.jinxuliang.mybatisspring.model.User;
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
