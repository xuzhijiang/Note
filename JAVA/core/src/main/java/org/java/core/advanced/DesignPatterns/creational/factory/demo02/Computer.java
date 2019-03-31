package org.java.core.advanced.DesignPatterns.creational.factory.demo02;

/**
 * 工厂设计模式中的super class可以是接口，抽象类或普通的java类。
 *
 */
public abstract class Computer {
	
	public abstract String getRAM();
	public abstract String getHDD();
	public abstract String getCPU();
	
	public String toString() {
		return "RAM= "+this.getRAM()+", HDD="+this.getHDD()+", CPU="+this.getCPU();
	}
}
