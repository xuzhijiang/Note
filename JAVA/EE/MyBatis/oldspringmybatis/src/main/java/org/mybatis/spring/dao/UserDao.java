package org.mybatis.spring.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.domain.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDao extends SqlSessionDaoSupport {

    private static String NAMESPACE = "org.mybatis.spring.mapper.UserMapper";

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    public User selectById(int id) {
        User user = sqlSessionTemplate.selectOne(NAMESPACE + ".selectById", id);
        return user;
    }

    // mybatis提供了抽象类SqlSessionDaoSupport，调用其getSqlSession()方法你会得到一个 SqlSessionTemplate,
    // 之后可以用于执行 SQL 方法, 就像下面这样:
//    public User selectById(int id) {
//        User user = getSqlSession().selectOne(NAMESPACE + ".selectById",id);
//        return user;
//    }
}
