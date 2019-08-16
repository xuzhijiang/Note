package com.mybatis.core.legacy.stepfour;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 无论是使用SqlSessionTemplate，还是继承SqlSessionDaoSupport，我们都需要手工编写DAO类的代码。熟悉mybatis同学知道，SqlSession有一个getMapper()方法，可以让我们通过映射器接口的方式来使用mybatis,而不用自己手工编写DAO类的代码.
 */
public class MainApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/stepfour/applicationContext.xml");
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        /**
         * 直接这样操作显然是会报错的，因为UserMapper是一个接口，且不是spring管理的bean，
         * 因此无法直接注入。
         * 这个时候，MapperFactoryBean登场了，通过xml配置，MapperFactoryBean会针对UserMapper
         * 接口创建一个代理Class对象,通过这个代理Class对象生成代理对象，并将其变成spring的一个bean。
         */
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(1);
        System.out.println(user);
    }
}
