package org.java.core.base.multithreading.lock;

public class Test {
	
	public synchronized void foo(){
	    //do something
		bar();
	}

	public synchronized void bar(){
		//do some more
	}
}
