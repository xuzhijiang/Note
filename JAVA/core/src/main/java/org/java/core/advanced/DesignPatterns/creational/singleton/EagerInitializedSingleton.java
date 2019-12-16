package org.java.core.advanced.DesignPatterns.creational.singleton;

/**
 * <br>如果你的singleton类没有使用大量的资源，那么就可以使用这个方法，然而在大多数场景中
 * Singleton类是为文件系统，数据库连接等资源创建的，(也就是singleton类没有使用大量资源的情况比较少)
 *   所以除非client 应用调用getInstance(),否则我们应该避免实例化，而且这种方法也没有提供处理异常的选项.
 */
public class EagerInitializedSingleton {
	
	//the instance of Singleton Class is created at the time of class loading.
	//this is the easiest method to create a singleton class but it has a drawback 
	//that instance is created even though client application might not be using it.
	private static final EagerInitializedSingleton instance = new EagerInitializedSingleton();
	
	//private constructor to avoid client applications to use constructor.
	private EagerInitializedSingleton() {}// 将构造器声明为私有的，只有singleton内部可以调用构造器
	
	public static EagerInitializedSingleton getInstance() {
		return instance;
	}
}
