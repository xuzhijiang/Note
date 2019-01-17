package org.java.core.advanced.DesignPatterns.creational.singleton;

public class EnumSingleton {
	
//	To overcome this situation with Reflection, Joshua Bloch 
//	suggests the use of Enum to implement Singleton design pattern (使用枚举实现单例设计模式)
//	as Java ensures that any enum value is instantiated only once in (任何枚举值在java程序中只实例化一次)
//	a Java program. Since Java Enum values are globally accessible, (java枚举值和单例一样，都是全局访问的)
//	so is the singleton. The drawback is that the enum type is somewhat (缺点是枚举有些不灵活)
//	inflexible; for example, it does not allow lazy initialization(延时初始化).
	
	// INSTANCE;
	
	public static void doSomething(){
	        //do something
	}
}
