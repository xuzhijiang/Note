package org.java.core.base.multithreading.simple;

/**
 * The reason for data inconsistency is because updating 
 * any field value is not an atomic process, it requires 
 * three steps; first to read the current value, second to 
 * do the necessary operations to get the updated value and 
 * third to assign the updated value to the field 
 * reference.数据不一致的原因是因为更新任何字段值不是原子过程，它需要三个步骤; 首先读取当前值，第二个读取必要的操作以获取更新的值，第三个将更新的值分配给字段引用。
 * <p>
 * 
 */
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