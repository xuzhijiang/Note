package org.java.core.base.multithreading.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyLockExample implements Runnable {

	private Resource resource;
	private Lock lock;

	public ConcurrencyLockExample(Resource r) {
		this.resource = r;
		this.lock = new ReentrantLock();
	}

	@Override
	public void run() {
		//正如你所看到的，我正在使用tryLock（）方法来确保我的线程只等待一定的时间，
		//如果它没有获得对象的锁，它只执行doLogging()
		
		//try-finally块来确保即使doSomething（）方法调用抛出任何异常也会释放锁定。
		try {
			//tryLock等待锁某一个给定的时间
			if (lock.tryLock(10, TimeUnit.SECONDS)) {
				resource.doSomething();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// release lock
			lock.unlock();
		}
		resource.doLogging();
	}

}
