package com.mybatis.core.SqlSessionDaoSupport;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class UserDao extends SqlSessionDaoSupport {

    private static String NAMESPACE = "com.mybatis.core.SqlSessionDaoSupport.UserMapper";

    public User selectById(int id) {
        // 我们把SqlSessionFactory设置给了SqlSessionDaoSupport
        // SqlSessionDaoSupport的getSqlSession()方法得到一个SqlSessionTemplate,之后可以用于执行 SQL 方法
        // sqlSessionTemplate内部执行sql 其实还是使用SqlSession (相当于代理SqlSession,然后添加前置后置操作)
        User user = getSqlSession().selectOne(NAMESPACE + ".selectById", id);
        /**
         * SqlSessionDaoSupport需要一个 sqlSessionFactory 或 sqlSessionTemplate 属性来设置.
         * 如果两者都被设置了,那么SqlSessionFactory是被忽略的
         */
        return user;
    }
}
