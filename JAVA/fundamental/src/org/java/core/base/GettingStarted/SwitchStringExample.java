package org.java.core.base.GettingStarted;

/**
 * 无论你有一个对不同输入String行为不同的简单方法，
 * 还是一个Servlet控制器类来检查传入的动作并相应地处理它.
 * 
 *  在Java 7之前，实现基于字符串的条件流的唯一方法是使用if-else条件。 
 *  但是Java 7改进了switch case以支持String。
 *  
 *  1. Java switch case通过删除多个if-else-if链接条件，使代码更具可读性。
 *  2. Java switch case String区分大小写
 *  3. Java Switch case uses String.equals() method to compare 
 *  the passed value with case values, so make sure to add a 
 *  NULL check to avoid NullPointerException.
 *  Java switch case用String的equals方法比较传入的值和case的值，以确保
 *  增加了一个是否为null的判断以避免空指针异常
 *  4. According to Java 7 documentation for Strings in Switch, 
 *  java compiler generates more efficient byte code for String 
 *  in Switch statement than chained if-else-if statements.
 *  根据Java7的对于在Switch中的String中的文档，java编译器生成更加有效的字节码对于
 *  Stirng在switch中的比起链式的if-else-if语句.
 *  5. 确保只有在知道它将与Java 7一起使用时才使用java switch case String，
 *  否则它将抛出Exception。
 *  6. 我们可以使用java三元运算符而不是switch来编写更小的代码。
 */
public class SwitchStringExample {

	public static void main(String[] args) {
		printColorUsingSwitch("red");
		
		printColorUsingIf("red");
		
		// switch case string is case sensitive
		printColorUsingSwitch("RED");
		printColorUsingSwitch(null);
	}

	private static void printColorUsingIf(String color) {
		if (color.equals("blue")) {
			System.out.println("BLUE");
		} else if (color.equals("red")) {
			System.out.println("RED");
		} else {
			System.out.println("INVALID COLOR CODE");
		}
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
