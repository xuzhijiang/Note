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

/**
 * 单机环境下,加锁的一般步骤
 * 1. 尝试加锁
 * 2. 如果锁没有被占用,则加锁成功
 * 3. 如果锁被占用,则等待锁被释放
 */
public class MysqlLock implements Lock {

    public static SqlSession session;

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String LOCK_NAME = "LOCK";

    // 1. 对应上面的尝试去加锁,也就是到mysql数据库中去"查询"这个锁没有被占用.
    @Override
    public boolean tryLock() {
        // 查询
        // for update(排它锁)把锁的实现机制推到了mysql层面来实现了.
        // 使用了
        LockMapper lockMapper = session.getMapper(LockMapper.class);
        int count = lockMapper.get(LOCK_NAME);

        // 如果等于0说明可以获得锁
        if (count == 0) {
            return true;
        }

        return false;
    }

    // 2. 如果锁没有被占用,就去加锁,也就是往mysql中insert数据
    @Override
    public void lock() {
        // insert

        while (true) {
            // tryLock()有可能多个线程同时返回true,导致锁失效
            // 看org.distributed.core.lock.mysqllock.MySqlForUpdateLock解决方案
            if (tryLock()) {
                LockMapper lockMapper = session.getMapper(LockMapper.class);
                lockMapper.insert(LOCK_NAME);
                return;
            } else {
                System.out.println("等待锁......");
            }
        }
    }

    // 3. 解锁
    @Override
    public void unlock() {
        LockMapper lockMapper = session.getMapper(LockMapper.class);
        lockMapper.delete(LOCK_NAME);
    }

    // 有的锁是可以被打断的
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }



    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
