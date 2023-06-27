package com.spring.mybatis.service;

import com.spring.mybatis.domain.User;
import com.spring.mybatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SqlSession sqlSession;

    public List<User> users() {
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        System.out.println(mapper.getClass());
        return userMapper.users();
    }
}
