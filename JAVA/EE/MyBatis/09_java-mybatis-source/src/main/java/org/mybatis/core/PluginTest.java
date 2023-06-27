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

/**
 * 自定义插件测试
 */
public class PluginTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testPlugin() throws IOException {
        SqlSession openSession = getSqlSessionFactory().openSession();
        try {
            UserMapper mapper = openSession.getMapper(UserMapper.class);
            User user = mapper.selectById(5);
            System.out.println(mapper);
            System.out.println(user);
        } finally {
            openSession.close();
        }
    }
}
