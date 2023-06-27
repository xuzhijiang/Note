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
 * 运行原理测试
 */
public class MyBatisCoreTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test01() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            // mybatis使用的是jdk的代理来生成代理对象.
            // 这个mapper类型为: org.apache.ibatis.binding.MapperProxy, 后续都是使用这个MapperProxy来进行增删改查
            // 我们看源码主要就是看怎么获得的这个 MapperProxy类的对象.
            UserMapper mapper = openSession.getMapper(UserMapper.class);
            // MapperProxy是一个java.lang.reflect.InvocationHandler,执行mapper.selectById(5)就回去执行
            // java.lang.reflect.InvocationHandler的invoke()方法.

            // MapperProxy类invoke()的部分代码:
            // 如果执行的方法是继承自Object的方法,不是接口的方法,就直接执行,否则都else
            // if (Object.class.equals(method.getDeclaringClass())) {
            // 执行继承自object类的方法
            //        return method.invoke(this, args);
            //      } else {执行接口中的方法}
            User user = mapper.selectById(5);
            System.out.println(mapper);
            System.out.println(user);
        } finally {
            openSession.close();
        }
    }

}
