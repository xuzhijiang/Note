package org.java.core.base.multithreading.simple;

public class ThreadRunExample {
	public static void main(String[] args) {
		Thread t1 = new Thread(new HeavyWorkRunnable(), "T1");
		Thread t2 = new Thread(new HeavyWorkRunnable(), "T2");
		t1.start();
		t2.start();
		
		Thread t3 = new Thread(new HeavyWorkThread(), "T3");
		Thread t4 = new Thread(new HeavyWorkThread(), "T4");
		t3.start();
		t4.start();
	}
}
