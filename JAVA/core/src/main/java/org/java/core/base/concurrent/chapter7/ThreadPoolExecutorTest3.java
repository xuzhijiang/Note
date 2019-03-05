package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 有界队列
 */
public class ThreadPoolExecutorTest3 {

    //测试corePoolSize和MaximumPoolSize随着任务提交量的变化,以及keepAliveTime与TimeUnit
    @Test
    public void threadPoolExecutorTest() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;

        // 立即提交的情况下,也就是taskCount>maxPoolSize的情况
        int taskCount = 10;//将taskCount改为13，超出maxPoolSize设置的5

        // 有界队列的情况下，taskCount>maxPoolSize+taskQueueSize的情况
        //而如果将taskCount设置为13，将BlockingQueue设置为一个有界的LinkedBlockingDeque
        BlockingQueue workQueue = new LinkedBlockingDeque<>(4);//队列的大小设置为4

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, workQueue);
        doTest(keepAliveTime, taskCount, threadPoolExecutor);
    }

    private void doTest(int keepAliveTime, int taskCount, ThreadPoolExecutor threadPoolExecutor)
     throws InterruptedException {
        // executor刚刚创建的时候，是不会初始化所有线程的。只有等到有任务进来的时候，才会创建线程对象。

        // 如果加上这一行，就不会报java.util.concurrent.RejectedExecutionException:
        // 也就是提交的任务超出maxPoolSize+queueSize的时候，会抛出异常。我们可以使用预定的RejectedExecutionHandler策略来处理这个异常
        // 需要注意的是，只有是在直接提交，和有界队列的情况下，这种异常处理策略才是有效的，对于无界队列，永远不会抛出这个异常。
        // threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
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

        // 在这里，可以看到，线程池大小增加到2之后，连续4次提交的任务，都没有继续扩大。
        // 一直到第7次提交任务的时候，线程池才开始增大，这是因为，使用了有界LinkedBlockingDeque(这里设置为4)的时候，
        // 默认情况下，线程池增大到corePoolSize(这里设置的是2)之后，就不再继续变大。
        //
        // 只有等到LinkedBlockingDeque已经全部填满(4个任务就满了)，并且超出的时候，才会继续增加线程，而到9次提交任务的时候，
        // 线程池已经达到了maxPoolSize为5。
        // 此时线程池不能继续增大。当继续往线程池中，添加任务时，就抛出了异常。
        //
        //这意味，在默认的AbortPolicy策略下，使用有界任务队列的时候，线程池可以同时执行的最大任务数量为：maxPoolSize+queueSize，
        // 而在我们的案例中，这个值为maxPoolSize(5)+queueSize(4)=9，所以超出这个限制的时候抛出了异常。
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
