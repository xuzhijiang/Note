package org.java.core.base.multithreading.FutureTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * FutureTask是Future接口的基本具体实现，并提供异步处理。
 * 它包含启动和取消任务的方法，以及可以返回FutureTask状态的方法，
 * 无论它是完成还是取消,
 * 我们需要一个可调用对象来创建未来任务，然后我们可以使用Java线程池执行器来异步处理这些任务。
 * <p><br>
 * FutureTask没有任何好处，但是当我们想要覆盖一些Future接口方法
 * 并且不想实现Future接口的每个方法时，它会派上用场。
 */
public class FutureTaskExample {
	public static void main(String[] args) {
		MyCallable callable1 = new MyCallable(1000);//同一个包中的MyCallable不用引入
		MyCallable callable2 = new MyCallable(2000);

		FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
		FutureTask<String> futureTask2 = new FutureTask<String>(callable2);

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(futureTask1);
		executor.execute(futureTask2);

		while (true) {
			try {
				if (futureTask1.isDone() && futureTask2.isDone()) {
					System.out.println("Done");
					// shut down executor service
					executor.shutdown();
					return;
				}

				if (!futureTask1.isDone()) {
					// wait indefinitely for future task to complete
					//get方法无限期等待未来的任务完成
					System.out.println("FutureTask1 output=" + futureTask1.get());
				}

				System.out.println("Waiting for FutureTask2 to complete");
				////get方法等待200毫秒
				String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
				if (s != null) {
					System.out.println("FutureTask2 output=" + s);
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				// do nothing
			}
		}

	}
}
