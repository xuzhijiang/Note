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
	
	//2. We can change the int constants value in ThreadStatesConstant 
	//class but the below program will not throw any exception. 
	//Our program might not work as expected but if we change the enum constants, 
	//we will get compile time error that removes any possibility of runtime issues.
	
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
