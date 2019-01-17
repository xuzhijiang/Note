package org.java.core.advanced.DesignPatterns.creational.singleton;

/**
 * Prior to Java 5, java memory model had a lot of issues and above 
 * approaches used to fail in certain scenarios where too many threads 
 * try to get the instance of the Singleton class simultaneously. 
 * So Bill Pugh came up with a different approach to create the Singleton 
 * class using a inner static helper class. The Bill Pugh Singleton 
 * implementation goes like this;
 * <p><br>
 * ()在Java 5之前，java内存模型有很多问题和上述方法在某些情况下用于失败，
 *  在这些情况下，太多线程试图同时获取Singleton类的实例。
 *  Bill Push提出了一种使用内部静态助手类创建Singleton类的不同方法.
 *  <p><br>
 *  Notice the private inner static class that contains the instance 
 *  of the singleton class. When the singleton class is loaded, 
 *  SingletonHelper class is not loaded into memory and only when 
 *  someone calls the getInstance method, this class gets loaded and 
 *  creates the Singleton class instance.
 *  <p><br>
 *  This is the most widely used approach for Singleton class as 
 *  it doesn’t require synchronization. I am using this approach 
 *  in many of my projects and it’s easy to understand and implement also.
 */
public class BillPughSingleton {
	
	private BillPughSingleton() {}
	
	private static class SingletonHelper{
		private static final BillPughSingleton INSTANCE = new BillPughSingleton();
	}
	
	public static BillPughSingleton getInstance() {
		return SingletonHelper.INSTANCE;
	}
}
