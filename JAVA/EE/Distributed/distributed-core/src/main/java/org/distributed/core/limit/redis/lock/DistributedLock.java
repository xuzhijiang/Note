package org.distributed.core.limit.redis.lock;

import org.distributed.core.limit.redis.util.JedisConnectionUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

/**
 * 分布式锁: 不同进程之间的锁竞争
 *
 * 实现途径: 1. 数据库 2. zookeeper 有序节点 3.redis setnx
 *
 * 本例,基于 redis setnx 和 expire 实现分布式锁
 *
 * 基于Redis命令：SET key value NX EX max-lock-time
 *
 * 这里补充下： 从2.6.12版本后, 就可以使用set来获取锁, Lua 脚本来释放锁。
 * setnx是老黄历了，set命令nx,xx等参数, 是为了实现 setnx 的功能。
 *
 *  SET resource_name my_random_value NX PX 30000 jedis.set(key, value, "nx", "ex", 3);
 *  SET resource_name my_random_value NX EX 30 jedis.set(key, value, "nx", "ex", 3);
 */
public class DistributedLock {

    private String initLockKey(String lockName) {
        return "Lock-" + lockName;
    }

    /**
     * 获得锁
     * @param lockName  锁的名称
     * @param waitTimeout  获得锁的等待时间
     * @param lockTimeout  锁的过期时间
     * @return 返回null表示抢夺锁超时
     */
    public String acquireLock(String lockName, long waitTimeout, long lockTimeout) {
        // 唯一标识, 用于区分获得锁和释放锁的是同一用户
        final String identifier = UUID.randomUUID().toString();
        final String lockKey = initLockKey(lockName);
        int lockExpire = (int) (lockTimeout/1000);

        Jedis jedis = null;
        try {
            jedis = JedisConnectionUtil.getJedis();
            long end = System.currentTimeMillis() + waitTimeout;
            while (System.currentTimeMillis() < end) {
                // 在超时时间范围内轮训
                Long nx = jedis.setnx(lockKey, identifier);
                // 表示成功获得锁,然后设置超时时间
                if (nx == 1) {
                    jedis.expire(lockKey, lockExpire);
                    return identifier;
                } else {
                    // 休眠100ms,避免不必要的重试
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        System.out.println(Thread.currentThread().getName() + " 抢夺锁超时");
        return null;
    }

    /**
     * 获得锁
     * @param lockName  锁的名称
     * @param waitTimeout  获得锁的等待时间
     * @param lockTimeout  锁的过期时间
     * @return 返回null表示抢夺锁超时
     */
    public boolean acquireLock2(String lockName, long waitTimeout, long lockTimeout) {
        // 唯一标识, 用于区分获得锁和释放锁的是同一用户
        final String identifier = UUID.randomUUID().toString();
        final String lockKey = initLockKey(lockName);
        int lockExpire = (int) (lockTimeout/1000);

        Jedis jedis = null;
        try {
            jedis = JedisConnectionUtil.getJedis();
            long end = System.currentTimeMillis() + waitTimeout;
            while (System.currentTimeMillis() < end) {
                // 在超时时间范围内轮训
                String result = jedis.set(lockKey, identifier, "NX", "EX", lockExpire);
                if ("OK".equals(result)) {
                    return true;
                } else  {
                    // 休眠100ms,避免不必要的重试
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        System.out.println(Thread.currentThread().getName() + " 抢夺锁超时");
        return false;
    }

    /**
     * lua脚本释放锁: lua脚本内执行redis会保证原子性,不用担心事务操作
     */
    public boolean releaseLockBylua(String lockName, String identifier) {
        Jedis jedis = null;
        try {
            jedis = JedisConnectionUtil.getJedis();
            final String lockKey = initLockKey(lockName);
            String luaScript = "if redis.call(\"get\", KEYS[1])==ARGV[1] then " +
                    " return redis.call(\"del\", KEYS[1])" +
                    " else return 0 end";
            Long result = (Long)jedis.eval(luaScript, 1, lockKey, identifier);
            return result > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    public boolean releaseLockBylua2(String lockName, String identifier) {
        Jedis jedis = null;
        try {
            jedis = JedisConnectionUtil.getJedis();
            final String lockKey = initLockKey(lockName);
            String luaScript = "if redis.call(\"get\", KEYS[1])==ARGV[1] then " +
                    " return redis.call(\"del\", KEYS[1])" +
                    " else return 0 end";
            Long result = (Long)jedis.eval(luaScript, Collections.singletonList(lockKey), Collections.singletonList(identifier));
            return 1L == result ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 释放锁
     */
    public boolean releaseLock(String lockName, String identifier) {
        Boolean isRelease = false;
        final String lockKey = initLockKey(lockName);
        Jedis jedis = null;
        try {
            jedis = JedisConnectionUtil.getJedis();
            while (true) {
                // watch : 一般是和事务一起使用，当对某个key进行watch后如果其他的客户端对
                // 这个key进行了更改，那么本次事务会被取消，事务的exec会返回nil
                jedis.watch(lockKey);
                // 判断是否为同一把锁
                if (identifier.equals(jedis.get(lockKey))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    if (transaction.exec().isEmpty()) {
                        // 删除失败,重试
                        continue;
                    }
                    isRelease = true;
                } else {
                    // 不是当前线程持有的锁, 异常提示
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return isRelease;
    }

}
