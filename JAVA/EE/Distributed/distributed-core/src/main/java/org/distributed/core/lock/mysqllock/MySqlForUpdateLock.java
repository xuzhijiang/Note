package org.distributed.core.lock.mysqllock;

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

    public static SqlSession session;

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            // 关闭自动提交
            session = sqlSessionFactory.openSession(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String LOCK_NAME = "LOCK";

    @Override
    public boolean tryLock() {
        LockMapper lockMapper = session.getMapper(LockMapper.class);
        lockMapper.getForUpdate(LOCK_NAME);
        // 这里查询完成后,并没有自动提交,mysql底层的锁并没有被释放
        return true;
    }

    @Override
    public void lock() {
        if (tryLock()) {
            System.out.println("MySqlForUpdateLock 获取到锁了...");
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        // 释放mysql底层的锁,也就是提交commit
        session.commit();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
