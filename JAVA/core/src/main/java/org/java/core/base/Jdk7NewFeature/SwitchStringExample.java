package org.java.core.base.Jdk7NewFeature;

/**
 * Java 7改进了switch case以支持String
 *
 * switch case String大小写敏感
 *
 *  根据Java7的对于在Switch中的String中的文档，java编译器生成更加有效的字节码对于
 *  Stirng在switch中的比起链式的if-else-if语句.
 */
public class SwitchStringExample {

	public static void main(String[] args) throws InterruptedException {
		printColorUsingSwitch("RED");

		printColorUsingSwitch("red");

		printColorUsingSwitch(null);

	}

	private static void printColorUsingSwitch(String color) {
		switch (color) {
		case "blue":
			System.out.println("BLUE");
			break;
		case "red":
			System.out.println("RED");
			break;
		default:
			System.out.println("INVALID COLOR CODE");
		}
	}

}