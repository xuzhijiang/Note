package org.java.core.base.concurrent.chapter7;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    private void printPoolSize(ThreadPoolExecutor threadPoolExecutor){
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        int poolSize = threadPoolExecutor.getPoolSize();
        System.out.println("核心线程池大小: " + corePoolSize + ", 最大线程池大小: " + maximumPoolSize + ", 当前线程池大小: " + poolSize);
    }
}