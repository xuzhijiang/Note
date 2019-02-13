package org.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class DynamicSql {

    public final Logger logger = Logger.getLogger(getClass());

    public static SqlSessionFactory sqlSessionFactory;
    public SqlSession sqlSession;

    public UserMapper userMapper;

    @BeforeClass
    public static void testBefore() throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }

    @Before
    public void before() {
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void insertUser() {
        User user = new User();
        user.setName("xzj");
        user.setId(1);
        user.setAge(26);
        userMapper.insert(user);
    }

    @Test
    public void testSelect1() {
        // 查询条件
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("name", "xzj");

        List<User> userList = userMapper.select(params);
        logger.info("userList size: " + userList.size());
    }

    @Test
    public void testSelect2() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("name", "xzj");
        params.put("age", 26);

        List<User> userList = userMapper.select(params);
        logger.info("userList size: " + userList.size());
    }

    @Test
    public void testChoose() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //params.put("name", "xzj");
        //params.put("age", 26);

        List<User> userList = userMapper.selectChoose(params);
        logger.info("userList size: " + userList.size());
    }
}
