package org.java.core.base.multithreading.lock;

public class SynchronizedLockExample implements Runnable{

	private Resource resource;
	
	public SynchronizedLockExample(Resource r){
		this.resource = r;
	}
	
	@Override
	public void run() {
		//使用synchronized块获取Resource对象的锁
		synchronized (resource) {
			resource.doSomething();
		}
		resource.doLogging();
	}
}
