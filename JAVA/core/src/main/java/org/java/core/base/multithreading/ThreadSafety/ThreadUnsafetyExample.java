package org.java.core.base.multithreading.ThreadSafety;

public class ThreadUnsafetyExample {
	public static void main(String[] args) {
        UnSafetyProcessingThread pt = new UnSafetyProcessingThread();
		Thread t1 = new Thread(pt, "t1");
        t1.start();
        Thread t2 = new Thread(pt, "t2");
        t2.start();
        //wait for threads to finish processing
        try {
			t1.join();
			System.out.println("Thread t1 join");
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println("Processing count="+pt.getCount());
	}
}

class UnSafetyProcessingThread implements Runnable
{
	private int count;

	@Override
	public void run() {
		System.out.println("Thread - " + Thread.currentThread().getName());
		for(int i=1; i<5; i++) {
			processSomething(i);
			count++;
		}
		System.out.println("Thread ends - " + Thread.currentThread().getName() + " - count - " + count);
	}
	
	public int getCount() {
		return this.count;
	}
	
	private void processSomething(int i) {
		//processing some job
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}