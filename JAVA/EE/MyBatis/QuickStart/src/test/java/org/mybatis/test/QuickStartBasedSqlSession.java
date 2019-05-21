package org.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mybatis.core.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

// 有两种使用方式：

// 1. 直接操作SqlSession的相关方法：这种方式是最基础的使用方式，但是对于理解mybatis的工作原理，
// 却是最重要的，其他使用方式建立在这个基础上。(在实际开发中，直接使用SqlSession的情况越来越少，除非是老项目)

// 2. 通过Mapper接口。这是目前主流的使用方式。

// 测试代码基于junit框架编写
public class QuickStartBasedSqlSession {

    public static SqlSessionFactory sqlSessionFactory;

    public static String namespace = "org.mybatis.core.mapper.UserMapper";

    public SqlSession sqlSession;

    //通过SqlSessionFactoryBuilder构造SqlSessionFactory实例
    @BeforeClass
    public static void testBefore() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }

    // 每个单元测试方法(添加@Test注解的方法)在执行前，此方法都会被juint框架回调，
    // 创建一个新的SqlSession实例
    @Before
    public void before() {
        sqlSession = sqlSessionFactory.openSession();
    }

    // 测试添加，调用sqlSession的insert方法
    // 执行testInsert方法后，数据库中会增加两条记录
    @Test
    public void testInsert() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("aaaa");
        user1.setAge(27);
        User user2 = new User();
        user2.setId(2);
        user2.setName("bbbbb");
        user2.setAge(26);
        String statement = namespace + ".insert";
        sqlSession.insert(statement, user1);
        sqlSession.insert(statement, user2);
    }

    // 测试查询单条记录，调用sqlSession的selectOne方法
    @Test
    public void testSelectOne() {
        User user = sqlSession.selectOne(namespace + ".selectById", 1);
        assert user != null;
        System.out.println("id:" + user.getId() + ",name:" + user.getName() + ",age:" + user.getAge());
    }

    // 注意:testSelectList和testSelectMap调用的都是UserMapper.xml文件中，
    // id值为"selectAll"这个<select>元素中的sql。

    // 测试查询多条记录，并将结果封装到一个List中，调用sqlSession的selectList方法
    @Test
    public void testSelectList() {
        List<User> userlist = sqlSession.selectList(namespace + ".selectAll");
        System.out.println(userlist);
    }

    // 测试查询多条记录，并将结果封装到一个Map中，调用sqlSession的selectMap方法
    @Test
    public void testSelectMap() {
        Map<Integer, User> userMap = sqlSession.selectMap(namespace + ".selectAll", "id");
        System.out.println(userMap);
    }

    // 测试更新，调用sqlSession的update方法
    @Test
    public void testUpdate() {
        User user = sqlSession.selectOne(namespace + ".selectById", 2);
        assert user != null;
        user.setName("cccc");
        user.setAge(26);
        int updateCount = sqlSession.update(namespace + ".updateById", user);
        assert updateCount == 1;
    }

    // 测试删除，调用sqlSession的delete方法
    @Test
    public void testDelete() {
        int deleteCount = sqlSession.delete(namespace + ".deleteById", 1);
        assert deleteCount == 1;
    }

    // 测试删除，调用sqlSession的delete方法
    @Test
    public void testDeleteAll() {
        int deleteCount = sqlSession.delete(namespace + ".deleteAll");
        assert deleteCount == 1;
    }

    // 每个单元测试方法(添加@Test注解的方法)在执行后，此方法都会被juint框架回调，关闭SqlSession实例
    @After
    public void after() {
        sqlSession.close();
    }
}