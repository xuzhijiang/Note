package org.java.core.advanced.DesignPatterns.creational.AbstractFactoryPattern;

/**
 * PCFactory就是PC的工厂，这个工厂中有创建PC的实例化PC的方法
 */
public class PCFactory implements ComputerAbstractFactory {

	private String ram;
	private String hdd;
	private String cpu;
	
	public PCFactory(String ram, String hdd, String cpu){
		this.ram=ram;
		this.hdd=hdd;
		this.cpu=cpu;
	}
	
	@Override
	public Computer createComputer() {
		return new PC(ram,hdd,cpu);
	}

}
