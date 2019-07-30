package com.di.dao.impl;

import com.di.dao.UserDAO;

import java.util.Arrays;
import java.util.List;

// 模拟从MongoDB数据库中提取数据
public class MongoUserDAO implements UserDAO {

    @Override
    public List<String> getAllUserNames() {
        System.out.println("**** Getting usernames from MongoDB *****");
        return Arrays.asList("Bond","James","Bond");
    }

}