package org.mybatis.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.mapper.DepartmentMapper;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.mapper.UserMapperPlus;
import org.mybatis.core.model.Department;
import org.mybatis.core.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 测试增删改
     * 1、mybatis允许增删改直接定义以下类型返回值
     * 		Integer、Long、Boolean、void
     * 2、我们需要手动提交数据
     * 		sqlSessionFactory.openSession();===》手动提交
     * 		sqlSessionFactory.openSession(true);===》自动提交
     * @throws IOException
     */
    @Test
    public void testInsert() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user1 = new User(null, "aaaa", "aa@qq.com", "male");
            User user2 = new User(null, "bbbb", "bb@qq.com", "female");
            userMapper.addUser(user1);
            System.out.println("user1: " + user1.getId());
            userMapper.addUser(user2);
            System.out.println("user2: " + user2.getId());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user1 = new User(5, "Tom", "updated@qq.com", "female");
            boolean result = userMapper.updateUser(user1);
            System.out.println("updateUser结果: " + result);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDelete() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            long result = userMapper.deleteById(5);
            System.out.println("删除结果: " + result);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testGetUserByIdAndLastName01() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//            User user = userMapper.getUserByIdAndLastName01(6, "bbbb");
//            User user = userMapper.getUserByIdAndLastName02(6, "bbbb");
//            User user = userMapper.getUserByIdAndLastName03(6, "bbbb");

//            User user = new User();
//            user.setId(6);
//            user.setLastName("bbbb");
//            User result = userMapper.getUserByIdAndLastName04(user);
//            System.out.println("************" + result);

            Map<String, Object> map = new HashMap<>();
            map.put("id", 6);
            map.put("lastName", "bbbb");
            map.put("tableName", "user");
            User result = userMapper.getUserByIdAndLastName05(map);
            System.out.println("************" + result);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectList() throws IOException {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 将结果封装到一个List中
            List<User> userlist = userMapper.getUsersByLastNameLike("%b%");
            System.out.println(userlist);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectMap() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 将一条记录封装到一个map中
            Map<String, Object> map1 = userMapper.getUserByIdReturnMap(6);
            System.out.println(map1);
            System.out.println("************************");
            Map<String, User> map2 = userMapper.getUserByLastNameLikeReturnMap("%a%");
            System.out.println(map2);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSimpleResultMap() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperPlus userMapperPlus = sqlSession.getMapper(UserMapperPlus.class);
            User user = userMapperPlus.getUserById(6);
            System.out.println("**********user: " + user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDifResultMap() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapperPlus userMapperPlus = sqlSession.getMapper(UserMapperPlus.class);
//            User user = userMapperPlus.getUserAndDept01(6);
            User user = userMapperPlus.getUserAndDept02(6);
//            User user = userMapperPlus.getEmpByIdStep(6);
//            System.out.println("**********user: " + user);
            System.out.println("*************: " + user.getEmail());
            System.out.println("*************: " + user.getDept().getDepartmentName()); // 会懒加载

            User user1 = userMapperPlus.getUserByIdStep(6); // female
            System.out.println("----------user1: " + user1);
            System.out.println("----------user1: " + user1.getDept());

            User user2 = userMapperPlus.getUserByIdStep(8); // male
            System.out.println("----------user2: " + user2);
            System.out.println("----------user2: " + user2.getDept());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testGetDeptByIdPlus() {
        SqlSession sqlSession = getSqlSession();
        try {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
//            Department dept = departmentMapper.getDeptByIdPlus(2);
//            System.out.println(dept);
//            System.out.println(dept.getUsers());

            Department dept = departmentMapper.getDeptByIdStep(2);
            System.out.println(dept.getDepartmentName());
            System.out.println(dept.getUsers());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}