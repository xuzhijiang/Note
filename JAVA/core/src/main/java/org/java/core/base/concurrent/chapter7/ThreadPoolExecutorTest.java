package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试corePoolSize和MaximumPoolSize随着任务提交量的变化,以及keepAliveTime与TimeUnit
 */
public class ThreadPoolExecutorTest {

    @Test
    public void threadPoolExecutorTest() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;
        /**
         * 任务的数量
         */
        int taskCount = 5;

        // 需要注意的是,在本例中，线程池达到corePoolSize之后，立刻继续增长，
        // 这是因为我们使用的是SynchronousQueue的原因。这个队列的特点是，只要一有任务进来，
        // 就立马要找一个空闲的线程来运行这个任务，如果没有空闲的线程，就会抛出异常。
        BlockingQueue<Runnable> queue = new SynchronousQueue<>();

        // executor(线程池)刚刚创建的时候，是不会初始化所有线程的。只有等到有任务进来的时候，才会创建线程对象。
        // 在这个案例中,每次提交一个任务，创建一个线程。当达到maxPoolSize的时候，线程池不会继续增大。
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, queue);

        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        System.out.println("-------threadPoolExecutor (线程池)刚刚创建----------");
        printPoolSize(threadPool);

        for (int i = 1; i <= taskCount; i++) {
            threadPool.execute(new Task(threadPool,i));
            System.out.print("--------已提交任务"+ i +"个任务--------");
            printPoolSize(threadPool);
        }

        // 等到所有的任务都执行完
        TimeUnit.SECONDS.sleep(11);//休眠11秒
        System.out.println("---------所有的任务都执行完--------");
        printPoolSize(threadPool);

        // 当所有任务都执行完成的时候，maxPoolSize为5，而因为corePoolSize为2，所以过了keepAliveTime之后，会自动缩小corePoolSize。
        //此时maximumPoolSize>corePoolSize，当前时间再休眠keepAliveTime时间，测试多出corePoolSize的线程是否能自动销毁
        System.out.println("--休眠keepAliveTime，测试maximumPoolSize>corePoolSize的部分能否自动回收--");
        TimeUnit.SECONDS.sleep(keepAliveTime);
        printPoolSize(threadPool);
    }

    @Test
    public void threadPoolExecutorTest2() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;

        // 立即提交的情况下,也就是taskCount>maxPoolSize的情况
        int taskCount = 6; // 将taskCount改为6，超出maxPoolSize设置的5
        // 再次运行单元测试，就会看到类似以下的异常：
        //
        //java.util.concurrent.RejectedExecutionException:

        // 需要注意的是，在这个案例中，线程池达到corePoolSize之后，立刻继续增长，
        // 这是因为我们使用的是SynchronousQueue的原因。这个队列的特点是，只要一有任务进来，
        // 就立马要找一个空闲的线程来运行这个任务，如果没有空闲的线程，就会抛出异常。
        BlockingQueue<Runnable> queue = new SynchronousQueue<>();

        // 使用了默认的 ThreadPoolExecutor.AbortPolicy被拒绝的执行处理程序，
        // 也就是处理程序遭到拒绝将抛出运行时异常: RejectedExecutionException
        // executor刚刚创建的时候，是不会初始化所有线程的。只有等到有任务进来的时候，才会创建线程对象。
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, queue);


        // 如果加上这一行，就不会报java.util.concurrent.RejectedExecutionException:
        // threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        System.out.println("-------threadPoolExecutor刚刚创建----------");
        printPoolSize(threadPool);

        // 每次提交一个任务，创建一个线程。当达到maxPoolSize的时候，线程池不会继续增大。
        for (int i = 1; i <= taskCount; i++) {
            threadPool.execute(new Task(threadPool, i));
            System.out.print("--------已提交任务"+ i +"个任务--------");
            printPoolSize(threadPool);
        }

        //等到所有的任务都执行完
        TimeUnit.SECONDS.sleep(11);//休眠10秒
        System.out.println("---------所有的任务都执行完--------");
        printPoolSize(threadPool);

        //此时maximumPoolSize>corePoolSize，当前时间再休眠keepAliveTime时间，测试多出corePoolSize的线程是否能自动销毁
        System.out.println("---------休眠keepAliveTime，测试maximumPoolSize>corePoolSize的部分能否自动回收--------");
        // 当所有任务都执行完成的时候，maxPoolSize为5，而因为corePoolSize为2，所以过了keepAliveTime之后，会自动缩小corePoolSize。
        TimeUnit.SECONDS.sleep(keepAliveTime);
        printPoolSize(threadPool);
    }

    /**
     * 有界队列的情况下
     */
    @Test
    public void threadPoolExecutorTest3() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;

        // 有界队列的情况下:
        // 立即提交的情况下,也就是taskCount>maxPoolSize的情况
        // 将taskCount改为10，超出maxPoolSize设置的5
        int taskCount = 10;

        // 有界队列的情况下，taskCount>maxPoolSize+taskQueueSize的情况
        // 而如果将taskCount设置为13，将BlockingQueue设置为一个有界的LinkedBlockingDeque
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(4);//队列的大小设置为4

        // executor刚刚创建的时候，是不会初始化所有线程的。只有等到有任务进来的时候，才会创建线程对象。
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, queue);


        // 提交的任务超出maxPoolSize+queueSize的时候，会抛出异常。
        // 我们可以使用预定的RejectedExecutionHandler策略来处理这个异常

        // 需要注意的是，只有是在"直接提交"，和"有界队列"的情况下，这种异常处理策略才是有效的，对于无界队列，
        // 永远不会抛出这个异常。
        // 如果加上这一行，就不会报java.util.concurrent.RejectedExecutionException:
        // threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        System.out.println("-------threadPoolExecutor刚刚创建----------");
        printPoolSize(threadPool);

        // 每次提交一个任务，创建一个线程。当达到maxPoolSize的时候，线程池不会继续增大。
        for (int i = 1; i <= taskCount; i++) {
            threadPool.execute(new Task(threadPool,i));
            System.out.print("--------已提交任务"+ i +"个任务--------");
            printPoolSize(threadPool);
        }

        //等到所有的任务都执行完
        TimeUnit.SECONDS.sleep(11);//休眠10秒
        System.out.println("---------所有的任务都执行完--------");
        printPoolSize(threadPool);

        // 在这里，可以看到，线程池大小增加到2之后，连续4次提交的任务，都没有继续扩大。
        // 一直到第7次提交任务的时候，线程池才开始增大，这是因为，使用了有界LinkedBlockingDeque(这里设置为4)的时候，
        // 默认情况下，线程池增大到corePoolSize(这里设置的是2)之后，就不再继续变大。
        //
        // 只有等到LinkedBlockingDeque已经全部填满(4个任务就满了)，并且超出的时候，才会继续增加线程，而到9次提交任务的时候，
        // 线程池已经达到了maxPoolSize为5。此时线程池不能继续增大。当继续往线程池中，添加任务时，就抛出了异常。
        //
        // 这意味，在默认的AbortPolicy策略下，使用有界任务队列的时候，线程池可以同时执行的最大任务数量为：maxPoolSize+queueSize,
        // 而在我们的案例中，这个值为maxPoolSize(5)+queueSize(4)=9，所以超出这个限制的时候抛出了异常。
    }

    @Test
    public void threadPoolExecutorTest4() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        int keepAliveTime = 5;
        int taskCount = 9;

        // 无界队列的情况:
        //
        // 如果构建 LinkedBlockingDeque的时候，没有指定大小，即使用了无界的任务队列
        // 可以发现当前线程池的大小一直是corePoolSize：2，maxPoolSize失效了。
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>();

        // executor刚刚创建的时候，是不会初始化所有线程的。只有等到有任务进来的时候，才会创建线程对象。
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, queue);

        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        System.out.println("-------threadPoolExecutor刚刚创建----------");
        printPoolSize(threadPool);

        // 每次提交一个任务，创建一个线程。当达到maxPoolSize的时候，线程池不会继续增大。
        for (int i = 1; i <= taskCount; i++) {
            threadPool.execute(new Task(threadPool,i));
            System.out.print("--------已提交任务"+ i +"个任务--------");
            printPoolSize(threadPool);
        }

        //等到所有的任务都执行完
        TimeUnit.SECONDS.sleep(11);//休眠10秒
        System.out.println("---------所有的任务都执行完--------");
        printPoolSize(threadPool);

        // 此时maximumPoolSize>corePoolSize，当前时间再休眠keepAliveTime时间，测试多出corePoolSize的线程是否能自动销毁
        System.out.println("---------休眠keepAliveTime，测试maximumPoolSize>corePoolSize的部分能否自动回收--------");
        // 当所有任务都执行完成的时候，maxPoolSize为5，而因为corePoolSize为2，所以过了keepAliveTime之后，会自动缩小corePoolSize。
        TimeUnit.SECONDS.sleep(keepAliveTime);
        printPoolSize(threadPool);
    }

    private void printPoolSize(ThreadPoolExecutor threadPool){
        int corePoolSize = threadPool.getCorePoolSize();
        int maximumPoolSize = threadPool.getMaximumPoolSize();
        int poolSize = threadPool.getPoolSize();
        System.out.println("corePoolSize: " + corePoolSize + ", maximumPoolSize: " + maximumPoolSize + ", poolSize: " + poolSize);
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

        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                 keepAliveTime, TimeUnit.SECONDS, queue);
        //设置线程工厂
        // 在使用ThreadPoolExecutor的情况下，`笔者是建议编写自己的ThreadFactory的`，
        // 这样我们使用jstack工具查看内存中的线程的时候，就能很容易的看出来那些线程是属于这个线程池的，
        // 这对于我们在解决一些问题，是非常有用的(这是笔者解决一个生产环境的死锁问题后的总结)。
        threadPool.setThreadFactory(new NamedThreadFactory());

        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        for (int i=0;i<taskCount;i++) {
            System.out.println("提交任务: " + i);
            threadPool.execute(new Task(threadPool, i));
            printPoolSize(threadPool);
        }

        TimeUnit.SECONDS.sleep(11);
        System.out.println("所有任务都完成");
    }
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
            printPoolSize(threadPoolExecutor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printPoolSize(ThreadPoolExecutor threadPoolExecutor){
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        int poolSize = threadPoolExecutor.getPoolSize();
        System.out.println("第"+taskId+"个任务执行完, " + "corePoolSize: " + corePoolSize + ", maximumPoolSize: " + maximumPoolSize + ", poolSize: " + poolSize);
    }
}

/**
 * 我们可以完全可以创建一个自己的ThreadFactory，以下实现参考了Executors.defaultThreadFactory()。
 */
class NamedThreadFactory implements ThreadFactory {

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
        Thread t=new Thread(threadGroup, name);
        return t;
    }
}
