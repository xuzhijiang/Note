package org.mybatis.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RowBoundsTest {

    // 对应UserMapper.xml中的namespace
    public static String namespace = "org.mybatis.core.mapper.UserMapper";

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过SqlSessionFactoryBuilder构造SqlSessionFactory实例
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    private SqlSession getSqlSession() {
        SqlSession sqlSession = null;
        try {
            // 2、获取sqlSession实例，能直接执行已经映射的sql语句
            sqlSession = getSqlSessionFactory().openSession();
            // 自动提交默认是关闭的,此时就需要我们手动提交,sqlSession.commit();
            //sqlSession = getSqlSessionFactory().openSession(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }

    // 使用 RowBounds 分页，不需要在 sql 语句中写 offset, limit
    // 注意,使用RowBounds的时候,不要引入PageHelper插件的依赖,否则引入了PageHelper插件之后,再使用RowBounds,也会变成物理分页
    @Test
    public void testRowBounds() {
        SqlSession sqlSession = getSqlSession();
        int offset = 2; // 偏移,offset是从0开始,6就代表是从第7条record开始
        int limit = 6;
        RowBounds rowBounds = new RowBounds(offset, limit);
        User user = new User();
        user.setGender("male");
        List<User> users = sqlSession.selectList(namespace + ".selectByCondition", user, rowBounds);
        System.out.println("MyBatis原生的RowBounds分页测试=======>>>" + users);
    }

}
