package org.java.core.advanced.DesignPatterns.creational.singleton;

/**
 * Above implementation works fine and provides thread-safety 
 * but it reduces the performance because of cost associated 
 * with the synchronized method, although we need it only for 
 * the first few threads who might create the separate instances
 * (尽管我们只在可能创建单独实例的前几个线程中需要synchronized method)
 * to avoid this extra overhead(为了避免每次额外的开销) every time, double checked locking 
 * principle is used(双重检查锁定原理被使用). In this approach, the synchronized block 
 * is used inside the if condition(在这个方法中，synchronized块在if条件中使用)
 * with an additional check to ensure that only one instance of singleton class is created.
 * (使用额外的检查，以确保只有一个类的实例被创建)
 */
public class ThreadSafeSingleton {
	private static ThreadSafeSingleton instance;
    
    private ThreadSafeSingleton(){}
    
//    public static synchronized ThreadSafeSingleton getInstance(){
//        if(instance == null){
//            instance = new ThreadSafeSingleton();
//        }
//        return instance;
//    }
    
    public static synchronized ThreadSafeSingleton getInstance(){
      if(instance == null){
    	  synchronized(ThreadSafeSingleton.class) {
    		  if(instance == null) {
    			  instance = new ThreadSafeSingleton();
    		  }
    	  }
      }
      return instance;
  }
}
