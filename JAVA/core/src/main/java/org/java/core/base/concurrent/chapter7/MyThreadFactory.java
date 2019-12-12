package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建自己的ThreadFactory，以下实现参考了Executors.defaultThreadFactory()
 *
 * 通过提供不同的ThreadFactory，可以改变线程的名称、线程组、优先级、守护进程状态等。
 * 如果从newThread返回 null 时 ThreadFactory 未能创建线程，则执行程序将继续运行，但不能执行任何任务。
 */
public class MyThreadFactory implements ThreadFactory {

    private static AtomicInteger poolId;
    private static ThreadGroup threadGroup;
    private AtomicInteger threadId;
    private static String threadNamePrefix="NamedThreadPool";

    public MyThreadFactory() {
        poolId=new AtomicInteger();
        threadGroup=new ThreadGroup("NamedThreadFactory");
        threadId=new AtomicInteger();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = threadNamePrefix + "-pool-" + poolId.getAndIncrement() + "-thread-" + threadId;
        Thread t=new Thread(threadGroup, name);
        return t;
    }

    /**
     * 自定义线程工厂ThreadFactory测试
     *
     * 线程池中的线程默认都是根据ThreadFactory创建，如果在构建ThreadPoolExecutor的时候，
     * 没有指定ThreadFactory，默认就会使用Executors.defaultThreadFactory()
     * 获取ThreadFactory实例。ThreadFactory只定义了一个抽象方法，用于返回新的线程：
     *
     * public interface ThreadFactory {
     *     Thread newThread(Runnable r);
     * }
     */
    @Test
    public void ThreadFactoryTest() throws Exception {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;
        int taskCount = 13;

        // 创建有界队列
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(4);

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, queue);
        //设置线程工厂
        // 在使用ThreadPoolExecutor的情况下，`笔者是建议编写自己的ThreadFactory的`，
        // 这样我们使用jstack工具查看内存中的线程的时候，就能很容易的看出来那些线程是属于这个线程池的，
        // 这对于我们在解决一些问题，是非常有用的(这是笔者解决一个生产环境的死锁问题后的总结)。
        threadPool.setThreadFactory(new MyThreadFactory());

        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        for (int i=0;i<taskCount;i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 执行任务:");
            });
            printPoolSize(threadPool);
        }

        TimeUnit.SECONDS.sleep(11);
        System.out.println("所有任务都完成");
    }

    private void printPoolSize(ThreadPoolExecutor threadPool){
        int corePoolSize = threadPool.getCorePoolSize();
        int maximumPoolSize = threadPool.getMaximumPoolSize();
        int poolSize = threadPool.getPoolSize();
        System.out.println("corePoolSize: " + corePoolSize + ", maximumPoolSize: " + maximumPoolSize + ", poolSize: " + poolSize);
    }
}
