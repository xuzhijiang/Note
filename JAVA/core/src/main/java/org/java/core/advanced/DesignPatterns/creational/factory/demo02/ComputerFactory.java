package org.java.core.advanced.DesignPatterns.creational.factory.demo02;

/**
 *  我们可以将Factory类设计为Singleton，或者我们可以将返回子类的方法作为static的。
 *  
 * 请注意，基于输入参数，将创建并返回不同的子类。 getComputer是工厂方法。
 *
 */
public class ComputerFactory {
	
	public static Computer getComputer(String type, String ram, String hdd, String cpu) {
		if ("PC".equals(type)) {
			return new PC(ram, hdd, cpu);
		} else if ("Server".equals(type)) {
			return new Server(ram, hdd, cpu);
		}
		return null;
	}
}
