package org.java.core.base.Enum;

public class ThreadStatesConstant {
	
	public static final int START = 1;
	public static final int WAITING = 2;
	public static final int RUNNING = 3;
	public static final int DEAD = 4;

	public static void main(String[] args) {
		benefitsOfEnumOverConstants();
	}

	//If we look at the below example, we have two risks with 
	//using constants that are solved by enum.

	//1. We can pass any int constant to the simpleConstantsExample 
	//method but we can pass only fixed values to simpleEnumExample, 
	//so it provides type safety.(传递类型安全)
	// 我们可以将任何int常量传递给simpleConstantsExample方法，
	// 但是我们只能将固定值传递给simpleEnumExample，因此它提供了类型安全性。
	
	//2. We can change the int constants value in ThreadStatesConstant 
	//class but the below program will not throw any exception. 
	//Our program might not work as expected but if we change the enum constants, 
	//we will get compile time error that removes any possibility of runtime issues.
	// 我们可以在ThreadStatesConstant类中更改int常量值，但上面的程序不会抛出任何异常。 
	// 我们的程序可能无法按预期工作，但如果我们更改枚举常量，我们将得到编译时错误，消除运行时问题的任何可能性。
	
	private static void benefitsOfEnumOverConstants() {
		// Enum values are fixed
		simpleEnumExample(ThreadStates.START);
		simpleEnumExample(ThreadStates.WAITING);
		simpleEnumExample(ThreadStates.RUNNING);
		simpleEnumExample(ThreadStates.DEAD);
		simpleEnumExample(null);
		
		simpleConstantsExample(1);
		simpleConstantsExample(2);
		simpleConstantsExample(3);
		simpleConstantsExample(4);
		//we can pass any int constant
		simpleConstantsExample(5);
		// 我们可以在ThreadStatesConstant类中更改int常量值，但上面的程序不会抛出任何异常。 
		// 我们的程序可能无法按预期工作，但如果我们更改枚举常量，我们将得到编译时错误，消除运行时问题的任何可能性。
	}

	private static void simpleEnumExample(ThreadStates th) {
		if (th == ThreadStates.START) {
			System.out.println("Thread started");
		} else if (th == ThreadStates.WAITING) {
			System.out.println("Thread is waiting");
		} else if (th == ThreadStates.RUNNING) {
			System.out.println("Thread is running");
		} else {
			System.out.println("Thread is dead");
		}
	}

	private static void simpleConstantsExample(int i) {
		if (i == ThreadStatesConstant.START)
			System.out.println("Thread started");
		else if (i == ThreadStatesConstant.WAITING)
			System.out.println("Thread is waiting");
		else if (i == ThreadStatesConstant.RUNNING)
			System.out.println("Thread is running");
		else
			System.out.println("Thread is dead");
	}
}
