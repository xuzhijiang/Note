package org.java.core.base.multithreading.ScheduledThreadPoolExecutor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 有时我们需要定期执行任务或在特定延迟后执行任务。 
 * Java提供了Timer Class，通过它我们可以实现这一点，
 * 但有时我们需要并行运行类似的任务。 因此，创建多个Timer对象将是系统的开销，
 * 并且最好有一个计划任务的线程池。
 * <p><br>
 * Java通过实现ScheduledExecutorService接口的
 * ScheduledThreadPoolExecutor类提供预定的线程池实现.
 * <p><br>
 * 请注意，所有schedule（）方法都返回ScheduledFuture的实例，
 * 我们可以使用它来获取线程的线程状态信息和延迟时间。
 * <p><br>
 * 时间段是从池中第一个线程的开始，
 * 因此如果您将period指定为1秒并且您的线程运行5秒，
 * 那么下一个线程将在第一个工作线程完成它的执行后立即开始执行。
 */
public class ScheduledThreadPool {

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

		System.out.println("Current Time = " + new Date());
		for (int i = 0; i < 3; i++) {
			Thread.sleep(1000);
			WorkerThread worker = new WorkerThread("do heavy processing");
			// schedule to run after sometime
			scheduledThreadPool.schedule(worker, 5, TimeUnit.SECONDS);
			
			// schedule task to execute at fixed rate
//			scheduledThreadPool.scheduleAtFixedRate(worker, 0, 5, TimeUnit.SECONDS);
			
			//ScheduledExecutorService scheduleWithFixedDelay方法
			//可用于以初始延迟启动定期执行，然后以给定的延迟执行。 延迟时间是从线程完成执行的时间开始
			scheduledThreadPool.scheduleWithFixedDelay(worker, 1, 4,
					TimeUnit.SECONDS);
		}

		// add some delay to let some threads spawn by scheduler
		Thread.sleep(30000);

		scheduledThreadPool.shutdown();
		while (!scheduledThreadPool.isTerminated()) {
			// wait for all tasks to finish
		}
		System.out.println("Finished all threads");
	}

}
