package org.java.core.thirdParty.guava;

import com.google.common.cache.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Guava 并没有另起一个额外的线程来判断数据是否过期，过期就删掉.
 *
 * 应该是以下原因：
 *
 * 新起线程需要资源消耗。
 * 维护过期数据还要获取额外的锁，增加了消耗。
 *
 * 而在查询数据的时候顺带做了这些事情，但是如果该缓存迟迟没有被访问(也就是查询),也会存在数据不能被回收的情况.
 * 查询的时候，如果缓存过期了，就尝试进行删除过期缓存，(需要加锁,把缓存的数量count-1)
 *
 * 源码分析:
 *
 * 最终会发现在 com.google.common.cache.LocalCache 类的 2187 行比较关键。
 *
 * 再跟进去之前第 2182 行会发现先要判断 count 是否大于 0，这个 count 保存的是当前缓存的数量，并用 volatile 修饰保证了可见性。
 *
 * 2187行获取活的(未过期的)的值，然后再2761 行，根据方法名称可以看出是判断当前的 Entry 是否过期，该 entry 就是通过 key 查询到的。
 * 这里就很明显的看出是根据构建时指定的过期方式来判断当前 key 是否过期了。如果缓存过期了，就尝试进行删除过期缓存，(需要加锁,把缓存的数量count-1)。
 *
 * Guava Cache 为了满足并发场景的使用，核心的数据结构就是按照 ConcurrentHashMap 来的,
 * 它内部会维护两个队列 `accessQueue,writeQueue` 这样才可以按照顺序淘汰数据（类似于利用 LinkedHashMap 来做 LRU 缓存）。
 */
public class CacheLoaderTest {

    private static LoadingCache<Integer, AtomicLong> loadingCache;

    private final static Integer KEY = 1000;

    private final static LinkedBlockingQueue<Integer> QUEUE = new LinkedBlockingQueue<>(10);

    private void init() throws InterruptedException {
        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)// 构建了 LoadingCache 对象，在 2 秒钟内不写入数据时,缓存就被清空.(缓存就过期)
                .removalListener(new RemovalListener<Object, Object>() {// 回收缓存时候的回调
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
                        System.out.println("删除原因: " + removalNotification.getCause() + ", 要删除key: " + removalNotification.getKey() + ", 要删除value：" + removalNotification.getValue());
                    }
                }).build(new CacheLoader<Integer, AtomicLong>() {
                    @Override
                    public AtomicLong load(Integer integer) throws Exception {
                        // 当loadingCache 通过 Key 获取不到缓存时，默认返回 666
                        return new AtomicLong(666);
                    }
                });

        for (int i=10;i<15;i++) {
            QUEUE.put(i);
        }
    }

    /**
     * 判断是否需要报警
     * 实际场景: 从 Kafka 实时读取出应用系统的日志信息，该日志信息包含了应用的健康状况。
     *  如果在某一段时间内发生了n次异常信息，那么就需要作出报警。
     */
    private void checkAlert(Integer integer) {
        try {
            loadingCache.put(integer, new AtomicLong());
            System.out.println("put: " + integer);
            System.out.println("cache 大小: " + loadingCache.size() + ", cache所有内容: " + loadingCache.asMap().toString());

            // 为了能看出 Guava 是怎么删除过期数据的在获取缓存之前休眠了 1 秒钟.
            TimeUnit.SECONDS.sleep(1);
            System.out.println("休眠1秒");

            System.out.println("缓存的大小: " + loadingCache.size() + ", 缓存的所有内容: " + loadingCache.asMap().toString());
            // System.out.println("缓存的键KEY:" + KEY + ", 缓存的值VALUE: " +loadingCache.get(KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CacheLoaderTest cacheLoaderTest = new CacheLoaderTest();
        // 初始化数据
        cacheLoaderTest.init();

        while (true) {
            // 检索并且删除QUEUE队列的头元素,如果没有元素可用则等待指定的时间,这里为等待200毫秒
            // 调用此方法的时候，如果等待的时候线程被中断了，就抛出InterruptedException
            Integer integer = QUEUE.poll(200, TimeUnit.MILLISECONDS);
            if (integer == null) {
                break;
            }

            cacheLoaderTest.checkAlert(integer);
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println("缓存的大小: " + loadingCache.size() + ", 缓存的所有内容: " + loadingCache.asMap().toString());
    }

}
