package org.java.core.base.multithreading.lock;

/**
 * 如果一个线程进入foo（），它就会锁定Test对象(it has the lock on Test object),
 * 所以当它尝试执行bar（）方法时，该线程是被允许的去执行bar（）方法，
 * 因为它持有在Test对象上的锁,这里如同synchronized(this).
 * @author a
 *
 */
public class Test {
	
	public synchronized void foo(){
	    //do something
		bar();
	}

	public synchronized void bar(){
		//do some more
	}
}
