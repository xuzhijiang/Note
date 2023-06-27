package com.mybatis.core.MapperFactoryBean;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// org.mybatis.spring.mapper.MapperFactoryBean的作用演示
public class MainClass {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("MapperFactoryBean/applicationContext.xml");
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        /**
         * 直接这样操作显然是会报错:
         * Type interface com.mybatis.core.MapperFactoryBean.UserMapper is not known to the MapperRegistry.
         * 因为UserMapper是一个接口,解决方法:
         *
         * 第一种: 在spring的xml配置文件中的sqlSessionFactory这个bean中通过mapperLocations属性配置sql映射文件,
         * 然后再添加 扫描mapper接口: <mybatis-spring:scan base-package="com.mybatis.core.MapperFactoryBean"/>
         *
         * 第二种: 如果不配置第一种的配置,可以在spirng的xml配置文件中配置MapperFactoryBean，
         * 然后在MapperFactoryBean中配置 mapperInterface属性,把UserMapper注册,这样就可以通过
         * sqlSession.getMapper(UserMapper.class)获取UserMapper接口的代理对象
         */
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(6);
        System.out.println(user);
    }
}
