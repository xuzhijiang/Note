package org.mybatis.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.User;
import org.mybatis.core.model.UserExample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MBGTest {


    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 测试 MyBatis3Simple 逆向工程 生成的 mapper接口/JavaBean/xml
     */
    @Test
    public void testMyBatis3Simple() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            UserMapper mapper = openSession.getMapper(UserMapper.class);
            List<User> list = mapper.selectByExample(null);
            for (User user : list) {
                System.out.println(user.getId());
            }
        }finally{
            openSession.close();
        }
    }

    @Test
    public void testMyBatis3() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            UserMapper mapper = openSession.getMapper(UserMapper.class);
            // xxxExample就是封装查询条件的
            //所有的带条件的查询都是调这用个selectByExample,而这个example就是封装查询条件的

            //1、查询所有
            List<User> users = mapper.selectByExample(null);
            System.out.println("****************" + users);

            //2、封装Employee查询条件的example
            UserExample example = new UserExample();
            //创建一个Criteria，这个Criteria就是拼装查询条件
            //select id, last_name, email, gender, d_id from user WHERE ( last_name like '%e%' and gender = 1 ) or email like "%qq%"
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andLastNameLike("%e%");
            criteria.andGenderEqualTo("1");

            // 所有的and条件都放到创建的 第一个 criteria中,
            // 所有or的条件 放到 新创建 的 criteria2中,然后调用 example.or(criteria2);

            UserExample.Criteria criteria2 = example.createCriteria();
            criteria2.andEmailLike("%qq%");
            example.or(criteria2);

            List<User> list = mapper.selectByExample(example);
            for (User user : list) {
                System.out.println(user.getId());
            }
        }finally{
            openSession.close();
        }
    }

}
