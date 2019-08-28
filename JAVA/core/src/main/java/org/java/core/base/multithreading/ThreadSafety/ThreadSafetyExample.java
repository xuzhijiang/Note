package org.java.core.base.multithreading.ThreadSafety;

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
				count++;//����һ��atomic operation
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