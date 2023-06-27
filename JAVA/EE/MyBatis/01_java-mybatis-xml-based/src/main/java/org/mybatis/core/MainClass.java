package org.mybatis.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MainClass {

    // 多线程环境下不能这么用,SqlSession不是线程安全的,可能A线程把这个SqlSession关闭了,B线程再用就会出现问题
    // private SqlSession sqlSession;

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过SqlSessionFactoryBuilder构造SqlSessionFactory实例
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    private SqlSession getSqlSession() {
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSessionFactory().openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }

    @Test
    public void testInsert() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            // 每次当我们调用sqlSession的getMapper方法时，都会创建一个新的UserMapper接口的动态代理对象
            // 生成的动态代理类不是唯一的，而是每次都创建一个新的
            // mybatis内部会通过动态代理+反射技术来创建这个动态代理对象.
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            System.out.println("UserMapper接口的代理类: " + userMapper.getClass());
            User user1 = new User(6, "aaaa", "aa@qq.com", "male");
            User user2 = new User(7, "bbbb", "bb@qq.com", "female");
            int count = userMapper.insertUser(user1);
            System.out.println("影响的行数: " + count);
            count = userMapper.insertUser(user2);
            System.out.println("影响的行数: " + count);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectById() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectById(6);
            System.out.println("结果: " + user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}