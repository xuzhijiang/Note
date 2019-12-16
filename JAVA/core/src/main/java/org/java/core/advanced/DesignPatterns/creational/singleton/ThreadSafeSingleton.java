package org.java.core.advanced.DesignPatterns.creational.singleton;

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
      // 使用“双重检查加锁”的方式，在getInstance()中减少使用同步
        // 检查实例，不存在就进入同步区域，这样一来，就只会在第一次同步
        // (也就是在instance没有被创建的时候同步),以后就不会同步了。
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
