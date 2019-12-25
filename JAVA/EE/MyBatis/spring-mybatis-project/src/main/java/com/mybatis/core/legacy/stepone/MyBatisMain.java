package com.mybatis.core.legacy.stepone;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.Reader;

public class MyBatisMain {

    private static SqlSessionFactory sqlSessionFactory;

    private static Reader reader;

    static {
        try {
            reader = Resources.getResourceAsReader("config/stepone/mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = session.selectOne("com.mybatis.core.legacy.stepone.UserMapper.GetUserByID", 2);
            System.out.println(user);
        } finally {
            session.close();
        }
    }

}
