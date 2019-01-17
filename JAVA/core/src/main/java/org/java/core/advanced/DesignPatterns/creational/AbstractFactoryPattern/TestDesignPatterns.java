package org.java.core.advanced.DesignPatterns.creational.AbstractFactoryPattern;

public class TestDesignPatterns {

	public static void main(String[] args) {
		testAbstractFactory();
	}
	
	private static void testAbstractFactory() {
		// 对比工厂模式来学习
		Computer pc = ComputerFactory.getComputer(new PCFactory("2 GB","500 GB","2.4 GHz"));
		Computer server = ComputerFactory.getComputer(new ServerFactory("16 GB","1 TB","2.9 GHz"));
		System.out.println("AbstractFactory PC Config::"+pc);
		System.out.println("AbstractFactory Server Config::"+server);
	}
}
