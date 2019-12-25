package com.mybatis.core.legacy.stepsix;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * mybatis提供了抽象类SqlSessionDaoSupport，调用其getSqlSession()方法你会得到一个 SqlSessionTemplate,之后可以用于执行 SQL 方法, 就像下面这样:
 *
 * SqlSessionDaoSupport 需要一个 sqlSessionFactory 或 sqlSessionTemplate 属性来设置.如果两者都被设置了,那么SqlSessionFactory是被忽略的
 *
 * 事实上，如果你提供的是一个SqlSessionFactory，SqlSessionDaoSupport内部也会使用其来构造一个SqlSessionTemplate实例。
 */
public class UserDao extends SqlSessionDaoSupport {

    private static String NAMESPACE = "com.mybatis.core.legacy.stepsix.mapper.UserMapper";

    public User selectById(int id) {
        User user = getSqlSession().selectOne(NAMESPACE + ".selectById", id);
        return user;
    }
}
