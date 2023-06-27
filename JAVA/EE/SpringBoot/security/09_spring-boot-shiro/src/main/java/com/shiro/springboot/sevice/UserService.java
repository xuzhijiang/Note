package com.shiro.springboot.sevice;

import com.shiro.springboot.dao.UserMapper;
import com.shiro.springboot.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}