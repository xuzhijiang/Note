package com.distributed.lock.mysqllock;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MySqlForUpdateLock implements Lock {

    public static SqlSessionFactory sqlSessionFactory;

    private ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SqlSession getSqlSession() {
        // 关闭自动提交
        System.out.println(Thread.currentThread().getName() + "获取SqlSession: ");
        return sqlSessionFactory.openSession(false);
    }

    @Override
    public void lock() {
        while (true) {
            if (tryLock()) {
                // 2. 如果锁没有被占用,就去加锁,也就是往mysql中insert数据
                System.out.println(Thread.currentThread().getName() + "=====>>>获取到锁了...");
                return;
            }
        }
    }

    @Override
    public boolean tryLock() {
        SqlSession sqlSession = getSqlSession();
        System.out.println(Thread.currentThread().getName() + " ===> " + sqlSession);
        threadLocal.set(sqlSession);
        // 利用for update(排它锁) , 把锁的实现机制推到了mysql层面来实现了.
        int count = sqlSession.getMapper(LockMapper.class).selectForUpdate("LOCK_NAME");
        // 这里查询完成后,并没有自动提交,所以mysql底层的锁并没有被释放
        return true;
    }

    // 有的锁是可以被打断的
    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }

    // 3. 解锁
    @Override
    public void unlock() {
        // 释放mysql底层的锁,也就是提交commit
        System.out.println(Thread.currentThread().getName() + "===>释放锁!!!!");
        System.out.println(Thread.currentThread().getName() + " ===> " + threadLocal.get());
        threadLocal.get().commit(true); // 这个必须要设置为true,否则这个测试就通不过.
    }

    @Override
    public Condition newCondition() { return null; }
}
