package org.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// 基于Mapper接口的测试代码
public class QuickStartBasedMapper {

    public static SqlSessionFactory sqlSessionFactory;

    public SqlSession sqlSession;

    //添加UserMapper接口
    public UserMapper userMapper;

    //通过SqlSessionFactoryBuilder构造SqlSessionFactory实例

    @BeforeClass
    public static void testBefore() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }

    // 每个单元测试方法(添加@Test注解的方法)在执行前，创建一个新的SqlSession实例，
    // 并获得UserMapper接口的动态代理对象
    @Before
    public void before() {
        sqlSession = sqlSessionFactory.openSession();
        // 每次当我们调用sqlSession的getMapper方法时，都会创建一个新的动态代理类实例
        // 生成的动态代理类不是唯一的，而是每次都创建一个新的
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    // 测试添加，调用UserMapper的insert方法
    @Test
    public void testInsert() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("aaaa");
        user1.setAge(27);
        User user2 = new User();
        user2.setId(2);
        user2.setName("bbbb");
        user2.setAge(26);
        int insertCount = userMapper.insert(user1);
        assert insertCount == 1;

        insertCount = userMapper.insert(user2);
        assert insertCount == 1;
    }

    // 测试查询单条记录，调用UserMapper的selectById方法
    @Test
    public void testSelectOne() {
        User user = userMapper.selectById(1);
        assert user != null;
        System.out.println("id:" + user.getId() + ",name:" + user.getName() + ",age:" + user.getAge());
    }

    // 测试查询多条记录，并将结果封装到一个List中，调用userMapper的selectAll方法
    @Test
    public void testSelectAll() {
        List<User> userlist = userMapper.selectAll();
        System.out.println(userlist);
    }

    // 测试更新，调用userMapper的updateById方法
    @Test
    public void testUpdate() {
        User user = userMapper.selectById(2);
        assert user != null;
        user.setName("cccc");
        user.setAge(26);
        int updateCount = userMapper.updateById(user);
        assert updateCount == 1;
    }

    // 测试删除，调用userMapper的deleteById方法
    @Test
    public void testDelete() {
        int deleteCount = userMapper.deleteById(1);
        assert deleteCount == 1;
    }

    // 测试删除，调用userMapper的deleteAll方法
    @Test
    public void testDeleteAll() {
        int deleteCount = userMapper.deleteAll();
        assert deleteCount == 1;
    }

    // 每个单元测试方法(添加@Test注解的方法)在执行后，此方法都会被juint框架回调，关闭SqlSession实例
    @After
    public void after() {
        sqlSession.close();
    }
}