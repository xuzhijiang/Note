package org.java.core.base.multithreading.ThreadSafety;

/**
 * Java�е��̰߳�ȫ��һ���ǳ���Ҫ�����⡣Java�ṩ���̻߳���֧��,����֪��
 * ����߳����ڶ�ȡ�޸Ĺ�����������ʱ���ܵ������ݲ�һ�¡�
 * ���ݲ�һ�µ�ԭ������Ϊ�����κ��ֶ�ֵ����ԭ�ӹ��̣�����Ҫ�������裺
 * ���ȶ�ȡ��ǰֵ���ڶ�������Ҫ�Ĳ����Ի�ȡ���µ�ֵ�������������µ�ֵ������ֶ����á�
 * <p>�̰߳�ȫ��Java����ʹ���ǵĳ����ڶ��̻߳����а�ȫʹ�õĹ���,�����¼��ַ�ʽ:
 * <p>Synchronization is the easiest and most widely used 
 * tool for thread safety in java.
 * Synchronization���������Լ���㷺ʹ�õ������̰߳�ȫ�Ĺ�����java��
 * <p>Use of Atomic Wrapper classes from java.util.concurrent.atomic package. 
 * For JdbcQuickStartExample AtomicInteger,��ԭ�Ӱ�����,��java.util.concurrent.atomic����.
 * <p>Use of locks from {@link java.util.concurrent.locks} package.
 * ʹ����
 * <p>Using thread safe collection classes, check this post for usage 
 * of {@link java.util.concurrent.ConcurrentHashMap} for thread safety.
 * ʹ���̰߳�ȫ�ļ����࣬
 * <p>Using volatile(�ױ��) keyword with variables to make every thread read 
 * the data from memory, not read from thread cache.
 * ʹ�ô���volatile�ؼ��ֵı�����ʹÿ���̴߳��ڴ��ж�ȡdata�������Ǵ��̻߳����ж�ȡ.
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