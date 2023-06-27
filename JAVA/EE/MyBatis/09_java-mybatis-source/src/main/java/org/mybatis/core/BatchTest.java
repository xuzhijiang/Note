package org.mybatis.core;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.core.mapper.UserMapper;
import org.mybatis.core.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * mybatis批量执行测试 (把插件相关的打印都注释掉)
 */
public class BatchTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 批量执行和非批量操作时间对比
     *
     * 假设插入1w条数据:
     *
     * 批量执行速度效率高很多.!!!非常快.执行次数对比:
     *
     * 批量：预编译sql一次 + 设置参数1w次 + 执行（1次） ====> 批量插入耗时：9344MS
     * 设置参数: ==> Parameters: 1e050(String), bbb15ttt@qq.com(String), 1(String), 1(Integer)
     *
     * 非批量：预编译sql 1w次 + 设置参数 1w次+ 执行 1w次 ===> 批量插入耗时：16853MS
     *
     * 预编译sql==>  Preparing: INSERT INTO user(last_name, email, gender, d_id) VALUES (?, ?, ?, ?)
     * 设置参数 ==> Parameters: 7b1f9(String), bbb9ttt@qq.com(String), 1(String), 1(Integer)
     * 执行===>    Updates: 1
     *
     * 可以对比打印,就可以看到区别
     */
    @Test
    public void testNonBatch() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        UserMapper mapper = openSession.getMapper(UserMapper.class);
        long start = System.currentTimeMillis();
        try{
            for (int i = 0; i < 10000; i++) {
                mapper.insert(new User(UUID.randomUUID().toString().substring(0, 5), "bbb" + new Random().nextInt(20) + "ttt@qq.com", "1", 1));
            }
            openSession.commit();
            long end = System.currentTimeMillis();
            System.out.println("非批量插入耗时："+(end-start) + "MS");
        }finally{
            openSession.close();
        }
    }

    @Test
    public void testBatch() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 方式1: 修改全局配置文件mybatis-config.xml,设置defaultExecutorType为BATCH
        // 这样就可以获取一个能执行批量操作的批量执行器Batch Executor
        // 这样的缺点: 所有的Executor都会变成批量执行器Batch Executor,这个不是我们想要的

        // 方式2: 直接sqlSessionFactory.openSession(ExecutorType.BATCH);, 获取批量执行的SqlSession
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper mapper = openSession.getMapper(UserMapper.class);
        long start = System.currentTimeMillis();
        try{
            for (int i = 0; i < 10000; i++) {
                mapper.insert(new User(UUID.randomUUID().toString().substring(0, 5), "bbb" + new Random().nextInt(20) + "ttt@qq.com", "1", 1));
            }
            openSession.commit();
            long end = System.currentTimeMillis();
            System.out.println("批量插入耗时："+(end-start) + "MS");
        }finally{
            openSession.close();
        }
    }

    // Mybatis执行批量插入，能返回数据库主键列表吗？ 能，JDBC都能，Mybatis当然也能
    @Test
    public void testBatchGetPrimaryKey() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper mapper = openSession.getMapper(UserMapper.class);
        List<User> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        try{
            for (int i = 0; i < 10; i++) {
                User user = new User(UUID.randomUUID().toString().substring(0, 5), "bbb" + new Random().nextInt(20) + "ttt@qq.com", "1", 1);
                mapper.insert(user);
                list.add(user);
            }
            openSession.commit();
            long end = System.currentTimeMillis();
            System.out.println("批量插入耗时："+(end-start) + "MS");

            System.out.println("=======================>>>>>");
            System.out.println("=======================>>>>>");
            System.out.println("=======================>>>>>");
            System.out.println("=======================>>>>>" + Arrays.toString(list.toArray()));

        }finally{
            openSession.close();
        }
    }
}
