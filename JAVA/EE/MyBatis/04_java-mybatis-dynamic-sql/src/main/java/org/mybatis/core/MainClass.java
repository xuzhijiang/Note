package org.mybatis.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapperDynamicSQL;
import org.mybatis.core.model.Department;
import org.mybatis.core.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainClass {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }

    //测试if\where
    @Test
    public void testIf() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperDynamicSQL userMapper = sqlSession.getMapper(UserMapperDynamicSQL.class);
            //查询的时候如果某些条件没带可能sql拼装会有问题,解决方案:
            //1、给where后面加上1=1，以后的条件都and xxx.
            //2、mybatis使用where标签来将所有的查询条件包括在内。
            // mybatis就会将where标签中拼装的sql，多出来的and或者or去掉
            // 不过where依然可能有问题,因为where只会去掉第一个多出来的and或者or。
            // 不能去掉最后一个可能多出来的and 或者or
//            User user = new User(6, "bbbb", null, null);
            User user = new User(null, "bbbb", null, null);
            List<User> list = userMapper.getUsersByConditionIf(user);
            for (User u : list) {
                System.out.println(u);
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    // 后面多出的and或者or where标签不能解决,所以要引入trim
    @Test
    public void testTrim() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperDynamicSQL userMapper = sqlSession.getMapper(UserMapperDynamicSQL.class);
//            User user = new User(6, "bbbb", null, null);
            User user = new User(null, "bbbb", null, null);
            List<User> list = userMapper.getUsersByConditionTrim(user);
            for (User u : list) {
                System.out.println(u);
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }


    @Test
    public void testChoose() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperDynamicSQL userMapper = sqlSession.getMapper(UserMapperDynamicSQL.class);
//            User user = new User(null, "bbbb", null, null);
            User user = new User(null, null, null, null);
            List<User> list = userMapper.getUsersByConditionChoose(user);
            for (User u : list) {
                System.out.println(u);
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSet() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperDynamicSQL userMapper = sqlSession.getMapper(UserMapperDynamicSQL.class);
//            User user = new User(null, "bbbb", null, null);
            User user = new User(8, "ttttt", null, null);
            userMapper.updateUser(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testForEach() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperDynamicSQL userMapper = sqlSession.getMapper(UserMapperDynamicSQL.class);
            List<User> users = userMapper.getUsersByConditionForeach(Arrays.asList(6, 8));
            System.out.println(users);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testBatchSave() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperDynamicSQL userMapper = sqlSession.getMapper(UserMapperDynamicSQL.class);
            List<User> users = new ArrayList<>();
            users.add(new User(null, "user-fff", "fff@qq.com", "female", new Department(1)));
            users.add(new User(null, "user-ggg", "ggg@qq.com", "male", new Department(2)));
            users.add(new User(null, "user-hhh", "hhh@qq.com", "female", new Department(1)));

            userMapper.addUsers(users);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInnerParam() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperDynamicSQL userMapper = sqlSession.getMapper(UserMapperDynamicSQL.class);
            User user = new User();
            user.setLastName("%user%");
            List<User> list = userMapper.getUsersTestInnerParameter(user);
            for (User u : list) {
                System.out.println(u);
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}