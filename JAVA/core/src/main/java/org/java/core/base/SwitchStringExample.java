package org.java.core.base;

/**
 * Java 7改进了switch case以支持String
 * switch 不支持 long.支持常用的int/Integer，String，enum
 */
public class SwitchStringExample {

	public static void main(String[] args) throws InterruptedException {
		printColorUsingSwitch("RED");
		printColorUsingSwitch("red");

		printColorUsingSwitch(null);

	}

	private static void printColorUsingSwitch(String color) {
		// switch case String大小写敏感
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
