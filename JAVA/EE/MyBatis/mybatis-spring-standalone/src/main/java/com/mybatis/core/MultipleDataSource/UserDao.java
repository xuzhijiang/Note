package com.mybatis.core.MultipleDataSource;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class UserDao extends SqlSessionDaoSupport {

    private static String NAMESPACE = "com.mybatis.core.MultipleDataSource.UserMapper";

    public User selectById(int id) {
        User user = getSqlSession().selectOne(NAMESPACE + ".selectById", id);
        return user;
    }
}
