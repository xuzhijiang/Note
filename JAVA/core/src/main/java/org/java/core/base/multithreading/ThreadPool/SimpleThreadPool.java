package org.java.core.base.multithreading.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleThreadPool {

	public static void main(String[] args) {
		//java.util.concurrent.Executors提供java.util.concurrent.Executor接口的实现
		//以在java中创建线程池
		//Executors类使用ThreadPoolExecutor提供ExecutorService的简单实现，Executors中的提供的实现代码如下:
		
//		public static ExecutorService newFixedThreadPool(int nThreads) {
//	        return new ThreadPoolExecutor(nThreads, nThreads,
//	                                      0L, TimeUnit.MILLISECONDS,
//	                                      new LinkedBlockingQueue<Runnable>());
//	    }
		
		ExecutorService executor = Executors.newFixedThreadPool(5);//线程池中线程的数量
		for (int i = 0; i < 10; i++) {
			Runnable worker = new WorkerThread("" + i);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}

	//我们创建5个工作线程的固定大小的线程池,然后我们向此池提交10个作业，因为池大小为5，
	//它将开始处理5个作业，其他作业将处于等待状态，只要其中一个作业完成，
	//等待队列中的另一个作业将 由工作线程接收并执行。
}
