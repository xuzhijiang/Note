package org.java.core.base.multithreading.synchronization;

/**
 * Notice that lock Object is public and by changing it��s reference, we can
 * execute synchronized block parallel in multiple threads. Similar case is true
 * if you have private Object but have setter method to changeStr it��s reference.
 */
public class HackerCodeExample2 {

	public static void main(String[] args) {
		MyObject2 myObject = new MyObject2();
		Thread t1 = new Thread(new HackerRunnable2(myObject));
		t1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("execute myObject.doSomething");
		myObject.doSomething();
		System.out.println("execute myObject.doSomething over");
	}
}

class MyObject2 {
	public Object lock = new Object();

	public void doSomething() {
		synchronized (lock) {
			System.out.println("doSomething");
			// ...
		}
	}
}

class HackerRunnable2 implements Runnable {
	private MyObject2 obj;

	public HackerRunnable2(MyObject2 obj) {
		this.obj = obj;
	}

	@Override
	public void run() {
		//untrusted code
		//changeStr the lock object reference
		obj.lock = new Object();
		synchronized(obj.lock) {
			while(true) {
				//Hacker code
				System.out.println("Hacker code running");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}