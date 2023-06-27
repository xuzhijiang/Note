package com.qianfeng.service;

import com.qianfeng.dao.UserDAO;
import com.qianfeng.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService{

    //@Resource(name="userDAO2")
    @Resource
    private UserDAO userDAO;
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUserByUsername(String username) {
        return userDAO.queryUserByUsername(username);
    }
}
