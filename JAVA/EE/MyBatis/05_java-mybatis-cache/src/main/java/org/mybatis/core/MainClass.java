package org.mybatis.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.Department;
import org.mybatis.core.model.User;
import java.io.IOException;
import java.io.InputStream;

// 问题: 缓存的使用顺序?
public class MainClass {

    private SqlSessionFactory sqlSessionFactory;

    // 注意二级缓存必须要球同一个SqlSessionFactory,否则二级缓存不生效
    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        if (sqlSessionFactory == null) {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 通过SqlSessionFactoryBuilder构造SqlSessionFactory实例
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
         return sqlSessionFactory;
    }

    private SqlSession getSqlSession() {
        SqlSession sqlSession = null;
        try {
            // 2、获取sqlSession实例，能直接执行已经映射的sql语句
            sqlSession = getSqlSessionFactory().openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }

    /**
     * 两级缓存：
     * 一级缓存：sqlSession级别的缓存(一级缓存是一直开启的),作用范围比较小
     * 		也就是说一个SqlSession对象,它拥有他自己的一级缓存,另一个不同的SqlSession对象,就会创建
     * 		它自己的一级缓存.这两个一级缓存之间是不能共用的.(SqlSession级别缓存说白了就是一个Map)
     * 	    当一个SqlSession关闭之后,这个SqlSession的一级缓存也就不存在了.
     *
     * 		与数据库同一次SqlSession会话期间查询到的数据会放在本地缓存中。
     * 		以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
     *
     * 		一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出sql查询）：
     * 		1、sqlSession不同。
     * 		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
     * 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
     * 		4、sqlSession相同，手动清除了一级缓存（缓存清空）
     *
     * 二级缓存：（全局缓存）：是mapper级别的缓存,基于namespace级别的缓存：
     *                       一个namespace对应一个二级缓存：(注意二级缓存必须要球同一个SqlSessionFactory,否则二级缓存不生效)
     * 		工作机制：
     * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
     * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话再去查询的时候，就可以使用二级缓存中的内容；
     * 		3、不同namespace查出的数据会放在自己对应的缓存中（map）
     * 			效果：数据会从二级缓存中获取
     * 				查出的数据都会被默认先放在一级缓存中。
     * 				只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
     *
     * 	    注意: 每一个新的会话,都会先去看有没有这个namespace对应的二级缓存,如果没有,再去SqlSession的一级缓存.
     *
     * 		使用：
     * 			1）、Mybatis默认没有开启二级缓存,需要修改全局配置,开启二级缓存：<setting name="cacheEnabled" value="true"/>
     * 			2）、去mapper.xml中配置使用二级缓存：
     * 				<cache></cache>
     * 			3）、我们的POJO需要实现序列化接口(因为mybatis为了安全,二级缓存会使用序列化&反序列的技术,所以需要实现序列化接口)
     *
     * 和缓存有关的设置/属性：
     * 			1）、cacheEnabled=true：false：关闭缓存（只会关闭二级缓存）(一级缓存不会被关闭,一直可用的)
     * 			2）、每个select标签都有useCache="true"：
     * 					如果这个属性改为false：则不使用缓存（一级缓存依然使用，二级缓存不使用,会被关闭）
     * 			3）、*******重要:【每个增删改标签(insert/delete/update)的：flushCache="true"：（一级二级都会清除）】
     * 					增删改执行完成后就会清除缓存；
     * 					测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
     * 		    3.1)  [查询标签(select标签)]：flushCache="false"：
     * 						如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
     * 			4）、sqlSession.clearCache();只是清除当前session的一级缓存；和二级缓存没有关系.
     * 			5）、localCacheScope(本地缓存作用域)：（会影响一级缓存SESSION）；
     * 		                    	默认为SESSION: 当前会话的所有数据保存在会话SqlSession缓存中；
     * 								如果取值为STATEMENT：禁用一级缓存；(了解即可)
     *
     *          ******需要注意的就是第三条****,flushCache=true之后,增删改会清除一级和二级缓存.
     *
     *第三方缓存整合：
     *		1）、导入第三方缓存包即可；
     *		2）、导入与第三方缓存整合的适配包；官方有；(org.mybatis.caches:mybatis-ehcache)
     *		3）、mapper.xml中使用自定义缓存
     *		<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
     *
     * @throws IOException
     *
     */
    @Test
    public void testFirstLevelCache() throws IOException {
        // 每一次会话都会对应自己的一级缓存.
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 共用一级缓存演示
            User u1 = userMapper.selectById(6);
            User u2 = userMapper.selectById(6);
            System.out.println("一级缓存演示: " + (u1==u2));

            // 一级缓存失效情况演示(4种)

            //4、sqlSession相同，手动清除了一级缓存（缓存清空）
            u1 = userMapper.selectById(6);
            sqlSession.clearCache();
            u2 = userMapper.selectById(6);
            System.out.println("情况4: " + (u1==u2));

            // 1、sqlSession不同。
            SqlSession sqlSession2 = getSqlSession();
            User u3 = sqlSession2.getMapper(UserMapper.class).selectById(6);
            System.out.println("情况1: " + (u1==u3));

            //2、sqlSession相同，查询条件不同
            User u4 = userMapper.selectById(8);
            System.out.println("情况2: " + (u1==u4));

            // 3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
            User uu = new User(null, "user-iii", "iii@qq.com", "female", new Department(2));
            userMapper.insert(uu);
            sqlSession.commit();
            System.out.println("数据添加成功");
            User u5 = userMapper.selectById(6);
            System.out.println("情况3: " + (u5==u1));

            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    // 测试二级缓存(要根据发了几条sql语句来看二级缓存是否生效)
    @Test
    public void testSecondLevelCache() {
        SqlSession sqlSession1 = getSqlSession();
        SqlSession sqlSession2 = getSqlSession();
        SqlSession sqlSession3 = getSqlSession();
        try {
            User u1 = sqlSession1.getMapper(UserMapper.class).selectById(8);
            System.out.println("u1-----: " + u1);
            // 每次操作完成后,SqlSession需要commit或者close才可以看出缓存的效果.
            // 因为只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
            sqlSession1.commit();
//            sqlSession1.close();

            //第二次查询是从二级缓存中拿到的数据，并没有发送新的sql
            User u2 = sqlSession2.getMapper(UserMapper.class).selectById(8);
            System.out.println("u2------: " + u2);
            System.out.println("u1==u2? " + (u1==u2));
            sqlSession2.close();

            User u3 = sqlSession3.getMapper(UserMapper.class).selectById(8);
            System.out.println("u3==u1?: " + (u3==u1));
            sqlSession3.close();
        } finally {

        }
    }

}