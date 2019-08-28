package org.java.core.base.concurrent.chapter7.CustomThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 线程池的实现由两部分组成:
 *
 * 1. SimpleThreadPoolExecutor是线程池的公开接口
 * 2. WorkerThread用来实现执行任务的子线程。
 */
public class SimpleThreadPoolExecutor implements Executor {

    /**
     * 任务队列
     */
    private BlockingQueue<Runnable> taskQueue = null;

    /**
     * 通过一个ArrayList代表一个线程池
     */
    private List<WorkerThread> threads = new ArrayList<>();

    /**
     * 线程池是否停止的标记
     */
    private boolean isStopped = false;

    public SimpleThreadPoolExecutor(int numOfThreads, int maxNoOfTasks){
        taskQueue = new LinkedBlockingQueue<>();
        // 创建numOfThreads个线程
        for(int i=0;i < numOfThreads;i++){
            threads.add(new WorkerThread(taskQueue));
        }
        for(WorkerThread thread : threads){
            thread.start();
        }
    }

    /**
     * 为了执行一个任务，execute(Runnable task) 用Runnable 的实现作为调用参数。
     * 在内部，Runnable 对象被放入 阻塞队列 (Blocking Queue)，等待着被子线程取出队列。
     */
    @Override
    public void execute(Runnable task) {
        if(this.isStopped)
            throw new IllegalStateException("SimpleThreadPoolExecutor is stopped");
        this.taskQueue.add(task);
    }

    /**
     * 调用stop() 方法可以停止 SimpleThreadPoolExecutor 。在内部，
     * 调用 stop 先会标记isStopped 成员变量（为 true）。然后，线程池的每一个子线程都调用WorkerThread .stop()
     * 方法停止运行。
     *
     * 注意，如果调用了stop()之后，再调用线程池SimpleThreadPoolExecutor.execute() 方法
     * 会抛出 IllegalStateException 异常。
     */
    public synchronized void stop(){
        this.isStopped = true;
        for(WorkerThread thread : threads){
            // 循环中断每个线程
            thread.toStop();
        }
    }

}



// LinkedBlockingQueue的take方法实现如下：
//public E take() throws InterruptedException {
//    E x;
//    int c = -1;
//    final AtomicInteger count = this.count;
//    final ReentrantLock takeLock = this.takeLock;
//    takeLock.lockInterruptibly();//内部通过死循环尝试获取锁，每次循环都判断线程是否中断了，如果中断抛出异常
//    try {
//        while (count.get() == 0) {
//            notEmpty.await();//如果任务数量为0，当前线程进入等待状态，等待signal信号
//        }
//        x = dequeue();
//        c = count.getAndDecrement();
//        if (c > 1)
//            notEmpty.signal();
//    } finally {
//        takeLock.unlock();
//    }
//    if (c == capacity)
//        signalNotFull();
//    return x;
//}