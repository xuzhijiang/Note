package org.mybatis.core;

import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.User;
import org.mybatis.core.typehandler.UserStatus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class TypeHandlerTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testEnumUse(){
        UserStatus login = UserStatus.LOGIN;
        System.out.println("枚举的索引："+login.ordinal());
        System.out.println("枚举的名字："+login.name());

        System.out.println("枚举的状态码："+login.getCode());
        System.out.println("枚举的提示消息："+login.getMsg());
    }

    /**
     * 默认mybatis在处理枚举对象的时候保存的是枚举的名字：EnumTypeHandler
     * 改变使用：EnumOrdinalTypeHandler：
     */
    @Test
    public void testEnum() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            UserMapper mapper = openSession.getMapper(UserMapper.class);
            User user = new User("test_enum_name", "enum11@qq.com","1", 2, UserStatus.LOGIN);
            mapper.insertEnum(user);
            System.out.println("保存成功: "+user.getId());
            User u = mapper.selectById(user.getId());
            System.out.println(u.getUserStatus());
            openSession.commit();
        }finally{
            openSession.close();
        }
    }

}
