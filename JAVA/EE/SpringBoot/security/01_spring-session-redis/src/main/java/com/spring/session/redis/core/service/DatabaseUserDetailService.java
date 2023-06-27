package com.spring.session.redis.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("databaseUserDetailService")
public class DatabaseUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    /**
     * 在用户登陆时，spring会调用这个方法去获得user的信息（密码等），以对比页面传过来的用户名和密码是否正确。
     * @param userName
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String userName) {
        return userService.getUserDetailByUserName(userName);
    }

}
