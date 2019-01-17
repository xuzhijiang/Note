package org.java.core.advanced.DesignPatterns.creational.AbstractFactoryPattern;

public class ComputerFactory {

	public static Computer getComputer(ComputerAbstractFactory factory) {
		return factory.createComputer();
	}
}
