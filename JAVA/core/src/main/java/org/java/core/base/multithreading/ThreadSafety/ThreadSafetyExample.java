package org.java.core.base.multithreading.ThreadSafety;

/**
 * Java中的线程安全是一个非常重要的主题。Java提供多线程环境支持,我们知道
 * 多个线程用于读取修改共享对象的数据时可能导致数据不一致。
 * 数据不一致的原因是因为更新任何字段值不是原子过程，它需要三个步骤：
 * 首先读取当前值，第二个做必要的操作以获取更新的值，第三个将更新的值分配给字段引用。
 * <p>线程安全在Java中是使我们的程序在多线程环境中安全使用的过程,有以下几种方式:
 * <p>Synchronization is the easiest and most widely used 
 * tool for thread safety in java.
 * Synchronization是最容易以及最广泛使用的用于线程安全的工具在java中
 * <p>Use of Atomic Wrapper classes from java.util.concurrent.atomic package. 
 * For example AtomicInteger,用原子包裹类,在java.util.concurrent.atomic包中.
 * <p>Use of locks from {@link java.util.concurrent.locks} package.
 * 使用锁
 * <p>Using thread safe collection classes, check this post for usage 
 * of {@link java.util.concurrent.ConcurrentHashMap} for thread safety.
 * 使用线程安全的集合类，
 * <p>Using volatile(易变的) keyword with variables to make every thread read 
 * the data from memory, not read from thread cache.
 * 使用带有volatile关键字的变量，使每个线程从内存中读取data，而不是从线程缓存中读取.
 */
public class ThreadSafetyExample {
	public static void main(String[] args) {
		SafetyProcessingThread pt = new SafetyProcessingThread();
		Thread t1 = new Thread(pt, "t1");
		t1.start();
		Thread t2 = new Thread(pt, "t2");
		t2.start();
		// wait for threads to finish processing
		try {
			t1.join();
			System.out.println("Thread t1 join");
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Processing count=" + pt.getCount());
	}
}

class SafetyProcessingThread implements Runnable {
	private int count;
    //dummy object variable for synchronization
	private Object mutex = new Object();

	@Override
	public void run() {
		System.out.println("Thread - " + Thread.currentThread().getName());
		for (int i = 1; i < 5; i++) {
			processSomething(i);
			//Using sychronized block to read, increment and update count value synchronously.
			synchronized(mutex) {
				count++;//不是一个atomic operation
			}
		}
		System.out.println("Thread ends - " + Thread.currentThread().getName() + " - count - " + count);
	}

	public int getCount() {
		return this.count;
	}

	private void processSomething(int i) {
		// processing some job
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}