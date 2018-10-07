package org.java.core.base.multithreading.ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkerPool {

	public static void main(String args[]) throws InterruptedException {
		// RejectedExecutionHandler implementation
		RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

		// Get the ThreadFactory implementation to use
		ThreadFactory threadFactory = Executors.defaultThreadFactory();

		// creating the ThreadPoolExecutor
		//在初始化ThreadPoolExecutor时，我们将初始池大小保持为2，最大池大小保持为4，工作队列大小保持为2.
		//因此，如果有4个正在运行的任务并且提交了更多任务，
		//则工作队列将只保留其中的2个 其余部分将由RejectedExecutionHandlerImpl处理。
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);

		// start the monitoring thread
		MyMonitorThread monitor = new MyMonitorThread(threadPoolExecutor, 3);
		Thread monitorThread = new Thread(monitor);
		monitorThread.start();
		
		// submit work to the thread pool
		for (int i = 0; i < 10; i++) {
			threadPoolExecutor.execute(new WorkerThread("cmd" + i));
		}
		
		Thread.sleep(30000);
		// shut down the pool
		//启动有序关闭，其中执行先前提交的任务，但不会接受任何新任务。
		//如果已经关闭，则不会产生额外的影响
		//此方法不会等待先前提交的任务执行完成。 可以使用awaitTermination来做到这一点。
		threadPoolExecutor.shutdown();
		// shut down the monitor thread
		Thread.sleep(5000);
		monitor.shutdown();
	}

}
