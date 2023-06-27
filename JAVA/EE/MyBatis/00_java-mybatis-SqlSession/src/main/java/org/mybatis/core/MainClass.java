package org.mybatis.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MainClass {

    // 对应UserMapper.xml中的namespace
    public static String namespace = "org.mybatis.core.mapper.UserMapper";

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过SqlSessionFactoryBuilder构造SqlSessionFactory实例
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    private SqlSession getSqlSession() {
        SqlSession sqlSession = null;
        try {
            // 2、获取sqlSession实例，能直接执行已经映射的sql语句
            sqlSession = getSqlSessionFactory().openSession();
            // 自动提交默认是关闭的,此时就需要我们手动提交,sqlSession.commit();
            //sqlSession = getSqlSessionFactory().openSession(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }

    @Test
    public void testInsert() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            User user1 = new User(1, "aaaa", "aa@qq.com", "male");
            User user2 = new User(2, "bbbb", "bb@qq.com", "female");
            // namespace + id => 锁定到唯一的sql
            String statement = namespace + ".insert";
            int count = sqlSession.insert(statement, user1);
            System.out.println("执行sql影响的行数====>: " + count);
            count = sqlSession.insert(statement, user2);
            System.out.println("执行sql影响的行数=====>: " + count);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectOne() throws IOException {
        SqlSession sqlSession = getSqlSession();
        // org.apache.ibatis.session.defaults.DefaultSqlSession
        System.out.println("SqlSession的实现类: " + sqlSession.getClass());
        try {
            User user = sqlSession.selectOne(namespace + ".selectById", 1);
            System.out.println(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectList() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            // 将结果封装到一个List中
            List<User> userlist = sqlSession.selectList(namespace + ".selectAll");
            System.out.println(userlist);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectMap() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            // 将结果封装到一个Map中,这个map的key是id
            Map<Integer, User> userMap1 = sqlSession.selectMap(namespace + ".selectAll", "id");
            System.out.println(userMap1);

            System.out.println();
            System.out.println("=====================>>>>");
            System.out.println("=====================>>>>");
            System.out.println();

            // 将结果封装到一个Map中,这个map的key是lastName
            Map<String, User> userMap2 = sqlSession.selectMap(namespace + ".selectAll", "lastName");
            System.out.println(userMap2);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = getSqlSession();
        try {
            User user = sqlSession.selectOne(namespace + ".selectById", 1);
            user.setLastName("updated lastName");
            user.setEmail("updated Email@qq.com");
            int count = sqlSession.update(namespace + ".updateById", user);
            System.out.println("执行sql影响的行数: " + count);
            // 手动提交数据.
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDelete() {
        SqlSession sqlSession = getSqlSession();
        try {
            int count = sqlSession.delete(namespace + ".deleteById", 2);
            System.out.println("执行sql影响的行数: " + count);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}