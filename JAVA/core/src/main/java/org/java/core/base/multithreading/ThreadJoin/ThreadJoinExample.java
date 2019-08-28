package org.java.core.base.multithreading.ThreadJoin;

/**
 * Java Thread join方法可用于暂停当前线程执行，除非指定的线程已死。
 * There are three overloaded join functions.
 * <p>
 * <strong>public final void join():</strong><br>
 * <strong>public final synchronized void join(long millis):</strong><br>
 * 等待调用join的线程to be dead or wait for specified milliseconds. 
 * Since thread execution depends on OS implementation, 
 * it doesn’t guarantee that the current thread will wait only for given time.
 * <strong>public final synchronized void join(long millis, int nanos):</strong><br>
 */
public class ThreadJoinExample
{
	public static void main(String[] args) {
		Thread t1 = new Thread(new MyRunnable(), "t1");
		Thread t2 = new Thread(new MyRunnable(), "t2");
		Thread t3 = new Thread(new MyRunnable(), "t3");
		
		t1.start();
		
        //start second thread after waiting for 2 seconds or if it's dead
		try {
			//pause main thread 2000 milliseconds.
			//main thread等待t1 2秒，或者如果2秒内t1变为dead，main thread才会继续执行
			t1.join(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t2.start();
		
		//start third thread only when first thread is dead.
		//只有当第一个线程死的时候，或者执行完成，才去start第三个线程,也就是只有等第一个线程执行完成(变为dead状态)，主线程
		//才往下继续执行
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t3.start();
		
        //let all threads finish execution before finishing main thread
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All threads are dead, exiting main thread");
	}
}

class MyRunnable implements Runnable
{
	@Override
	public void run() {
		System.out.println("Thead started:::" + Thread.currentThread().getName());
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread ended:::" + Thread.currentThread().getName());
	}
}