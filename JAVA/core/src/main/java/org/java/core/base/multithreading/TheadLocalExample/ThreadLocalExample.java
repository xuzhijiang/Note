package org.java.core.base.multithreading.TheadLocalExample;

import java.text.SimpleDateFormat;
import java.util.Random;

public class ThreadLocalExample implements Runnable {

	// SimpleDateFormat is not thread-safe, so give one to each thread
	// 使get()方法返回值不用做强制类型转换，可以创建一个泛型化的ThreadLocal对象
	private static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	// 更新：ThreadLocal类在Java 8中使用新方法withInitial（）进行扩展，
	// 该方法将Supplier功能接口作为参数。 因此我们可以使用lambda表达式轻松创建ThreadLocal实例
	private static final ThreadLocal<SimpleDateFormat> formatter2 =
			ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

	public static void main(String[] args) throws InterruptedException {
		ThreadLocalExample obj = new ThreadLocalExample();
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(obj, "" + i);
			Thread.sleep(new Random().nextInt(1000));
			t.start();
		}
		// Thread-0已经改变了formatter的值，但是thread-2获得的值与初始化值相同。
	}

	@Override
	public void run() {
		System.out.println("Thread Name= " + Thread.currentThread().getName() +
				" default Formatter = " + formatter.get().toPattern());
		try {
			Thread.sleep(new Random().nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// formatter pattern is changed here by thread, but it won't reflect to other threads
		formatter.set(new SimpleDateFormat("yyyyMMDD HHmmss"));

		System.out.println("Thread Name= " + Thread.currentThread().getName() +
				" formatter = " + formatter.get().toPattern());
	}
}
