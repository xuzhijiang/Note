package org.java.core.base.multithreading.simple;

/**
 * Thread safety in java is the process to make our 
 * program safe to use in multithreaded environment, 
 * there are different ways through which we can make 
 * our program thread safe.
 * <p>线程安全在Java中是使我们的程序在多线程环境中安全使用的过程,有以下几种方式:
 * <p>Synchronization is the easiest and most widely used tool for thread safety in java.
 * <p>Use of Atomic Wrapper classes from java.util.concurrent.atomic package. For example AtomicInteger
 * <p>Use of locks from {@link java.util.concurrent.locks} package.
 * <p>Using thread safe collection classes, check this post for usage of {@link java.util.concurrent.ConcurrentHashMap} for thread safety.
 * <p>Using volatile keyword with variables to make every thread read the data from memory, not read from thread cache.
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
				count++;
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