package org.java.core.base.multithreading.synchronization;

/**
 * Notice that hacker code is getting lock on class monitor 
 * and not releasing it, it will cause deadlock and DoS in the system.
 * <p>
 * <p>请注意，黑客代码在类监视器上获得锁定而不释放它，它将导致系统中的死锁和DoS。
 */
public class HackerCodeExample3 {
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new HackerRunnable3());
		t1.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("execute myObject.doSomething");
		MyObject3.doSomething();
		System.out.println("execute myObject.doSomething over");
	}
}

class MyObject3
{
	//locks on the class object's monitor
	//锁定类对象的监视器，方法名称前面加上static synchronized关键字
	//等价于此方法用用当前类的class当做锁.
	public static synchronized void doSomething() {
		System.out.println("Myobject doSomething");
		//....
	}
}

class HackerRunnable3 implements Runnable
{
	@Override
	public void run() {
		synchronized(MyObject3.class) {
			while(true) {
				//Hacker code
				System.out.println("Hacker code running");
				try {
					Thread.sleep(1000);
//				    Thread.sleep(Integer.MAX_VALUE); // Indefinitely delay MyObject
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}