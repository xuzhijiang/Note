package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest2 {

    //测试corePoolSize和MaximumPoolSize随着任务提交量的变化,以及keepAliveTime与TimeUnit
    @Test
    public void threadPoolExecutorTest() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;

        // 立即提交的情况下,也就是taskCount>maxPoolSize的情况
        int taskCount = 6;//将taskCount改为6，超出maxPoolSize设置的5
        // 再次运行单元测试，就会看到类似以下的异常：
        //
        //java.util.concurrent.RejectedExecutionException:

        // 需要注意的是，在这个案例中，线程池达到corePoolSize之后，立刻继续增长，
        // 这是因为我们使用的是SynchronousQueue的原因。这个队列的特点是，只要一有任务进来，
        // 就立马要找一个空闲的线程来运行这个任务，如果没有空闲的线程，就会抛出异常。
        BlockingQueue workQueue = new SynchronousQueue();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, workQueue);
        doTest(keepAliveTime, taskCount, threadPoolExecutor);
    }

    private void doTest(int keepAliveTime, int taskCount, ThreadPoolExecutor threadPoolExecutor)
     throws InterruptedException {
        // executor刚刚创建的时候，是不会初始化所有线程的。只有等到有任务进来的时候，才会创建线程对象。
        //threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());//如果加上这一行，就不会报java.util.concurrent.RejectedExecutionException:
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
