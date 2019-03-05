package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.concurrent.*;

public class ThreadPoolExecutorTest4 {

    //测试corePoolSize和MaximumPoolSize随着任务提交量的变化,以及keepAliveTime与TimeUnit
    @Test
    public void threadPoolExecutorTest() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;
        int taskCount = 9;

        //无界队列的情况:
        //
        //如果构建 LinkedBlockingDeque的时候，没有指定大小，即使用了无界的任务队列
        // 可以发现当前线程池的大小一直是corePoolSize：2，maxPoolSize失效了。
        BlockingQueue workQueue = new LinkedBlockingDeque<>();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, workQueue);
        doTest(keepAliveTime, taskCount, threadPoolExecutor);
    }

    private void doTest(int keepAliveTime, int taskCount, ThreadPoolExecutor threadPoolExecutor)
     throws InterruptedException {
        // executor刚刚创建的时候，是不会初始化所有线程的。只有等到有任务进来的时候，才会创建线程对象。
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        System.out.println("-------threadPoolExecutor刚刚创建----------");
        printPoolSize(threadPoolExecutor);

        // 每次提交一个任务，创建一个线程。当达到maxPoolSize的时候，线程池不会继续增大。
        for (int i = 1; i <= taskCount; i++) {
            threadPoolExecutor.execute(new Task(threadPoolExecutor,i));
            System.out.print("--------已提交任务"+ i +"个任务--------");
            printPoolSize(threadPoolExecutor);
        }

        //等到所有的任务都执行完
        TimeUnit.SECONDS.sleep(11);//休眠10秒
        System.out.println("---------所有的任务都执行完--------");
        printPoolSize(threadPoolExecutor);

        //此时maximumPoolSize>corePoolSize，当前时间再休眠keepAliveTime时间，测试多出corePoolSize的线程是否能自动销毁
        System.out.println("---------休眠keepAliveTime，测试maximumPoolSize>corePoolSize的部分能否自动回收--------");
        // 当所有任务都执行完成的时候，maxPoolSize为5，而因为corePoolSize为2，所以过了keepAliveTime之后，会自动缩小corePoolSize。
        TimeUnit.SECONDS.sleep(keepAliveTime);
        printPoolSize(threadPoolExecutor);
    }

    private void printPoolSize(ThreadPoolExecutor threadPoolExecutor){
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        int poolSize = threadPoolExecutor.getPoolSize();
        System.out.println("核心线程池大小: " + corePoolSize + ", 最大线程池大小: " + maximumPoolSize + ", 当前线程池大小: " + poolSize);
    }

    class Task implements Runnable{
        private ThreadPoolExecutor threadPoolExecutor;
        private int taskId;

        public Task(ThreadPoolExecutor threadPoolExecutor,final int taskId) {
            this.threadPoolExecutor = threadPoolExecutor;
            this.taskId = taskId;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);//休眠10秒
                System.out.print("第"+taskId+"个任务执行完:");
                printPoolSize(threadPoolExecutor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
