package com.mybatis.core.SqlSessionTemplate;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * SqlSessionTemplate 是SqlSession接口的实现类.
 * 使用了SqlSessionTemplate之后，不再需要通过SqlSessionFactory.openSession()方法来创建SqlSession实例
 * 使用完成之后，也不要调用SqlSession.close()方法进行关闭
 *
 * 对于事务，SqlSessionTemplate 将会保证使用的 SqlSession 是和当前 Spring 的事务相关的。
 *
 * 之后我们可以在org.mybatis.spring.dao.UserDao类中直接进行注入.
 * SqlSessionTemplate 是线程安全的, 可以被多个 DAO 所共享使用
 * SqlSessionTemplate本质上是一个代理,代理的是SqlSession
 */
public class UserDao {

    private static String NAMESPACE = "com.mybatis.core.legacy.steptwo.mapper.UserMapper";
    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public User selectById(int id) {
        // 我们把SqlSessionFactory设置给了SqlSessionTemplate,然后sqlSessionTemplate内部操作
        // 其实还是使用SqlSession
        User user = sqlSessionTemplate.selectOne(NAMESPACE + ".selectById", id);
        return user;
    }
}
