package org.java.core.advanced.DesignPatterns.creational.singleton;

import java.io.Serializable;

public class SerializedSingleton implements Serializable{

	private static final long serialVersionUID = -5192695839088380039L;
	
	private SerializedSingleton() {}
	
	private static class SingletonHelper{
		private static final SerializedSingleton instance = new SerializedSingleton();
	}
	
	public static SerializedSingleton getInstance() {
		return SingletonHelper.instance;
	}
	
	//The problem with above serialized singleton class is that 
	//whenever we deserialize it, it will create a new instance of the class
	
	/**
	 * So it destroys the singleton pattern, to overcome this scenario all 
	 * we need to do it provide the implementation of readResolve() method.
	 * After this you will notice that hashCode of both the instances are same in test program.
	 */
	protected Object readResolve() {
		return getInstance();
	}
}
