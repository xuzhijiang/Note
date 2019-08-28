package org.java.core.base.concurrent.chapter7.CustomThreadPool;

import java.util.concurrent.BlockingQueue;

/**
 * 一个空闲的WorkerThread 线程会把 Runnable 对象从任务队列中取出并执行。
 * 执行完毕后，WorkerThread 进入循环并且尝试从队列中再取出一个任务，直到线程终止。
 */
class WorkerThread extends Thread {

    private BlockingQueue<Runnable> taskQueue = null;

    private boolean isStopped = false;

    public WorkerThread(BlockingQueue<Runnable> queue) {
        this.taskQueue = queue;
    }

    @Override
    public void run() {
        //因为需要不断从的任务列出中取出task执行，因此需要放在一个循环中，否则线程对象执行完一个任务就会立刻结束
        while(!isStopped()){
            try {
                // 如果WorkerThread没有停止，就从任务队列中获取任务,然后运行
                Runnable runnable = taskQueue.take();
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 写日志或者报告异常,但保持线程池运行.
            }
        }
    }

    /**
     * 注意WorkerThread.stop()方法中调用了this.interrupt()。
     * 它确保阻塞在taskQueue.take() 里的处理等待状态的线程能够跳出等待状态。
     */
    public synchronized void toStop() {
        isStopped = true;
        // 如果线程正在任务队列中获取任务，或者没有任务被阻塞，需要响应这个中断
        this.interrupt();
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }

}