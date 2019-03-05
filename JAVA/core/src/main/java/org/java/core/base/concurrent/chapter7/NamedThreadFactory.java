package org.java.core.base.concurrent.chapter7;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 我们可以完全可以创建一个自己的ThreadFactory，以下实现参考了Executors.defaultThreadFactory()。
 */
public class NamedThreadFactory implements ThreadFactory {

    private static AtomicInteger poolId;
    private static ThreadGroup threadGroup;
    private AtomicInteger threadId;
    private static String threadNamePrefix="NamedThreadPool";

    public NamedThreadFactory() {
        poolId=new AtomicInteger();
        threadGroup=new ThreadGroup("NamedThreadFactory");
        threadId=new AtomicInteger();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = threadNamePrefix + "-pool-" + poolId.getAndIncrement() + "-thread-" + threadId;
        Thread t=new Thread(threadGroup,name);
        return t;
    }
}
