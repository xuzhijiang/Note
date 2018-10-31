package org.java.core.base.generics;

/**
 * 注意isEqual方法签名展示了如何在方法中使用泛型的语法，
 * 也要注意如何在程序中使用此方法，
 * 
 *  我们可以在调用这些方法时指定类型，或者我们可以像普通方法一样调用它们。 
 *  Java编译器足够聪明，可以确定要使用的变量类型（本例中也就是可以确定g1和g2中的泛型的类型为String）
 *  ，此工具称为类型推断。
 *
 */
public class GenericsMethods {

	//Java Generic Method
	public static <T> boolean isEqual(GenericsType<T> g1, GenericsType<T> g2){
		return g1.get().equals(g2.get());
	}
	
	public static void main(String args[]){
		GenericsType<String> g1 = new GenericsType<>();
		g1.set("Pankaj");
		
		GenericsType<String> g2 = new GenericsType<>();
		g2.set("Pankaj");
		
		boolean isEqual = GenericsMethods.<String>isEqual(g1, g2);
		System.out.println(isEqual);
		//above statement can be written simply as
		isEqual = GenericsMethods.isEqual(g1, g2);
		System.out.println(isEqual);
		//This feature, known as type inference, 
		//allows you to invoke a generic method as an ordinary method, 
		//without specifying a type between angle brackets.
		//Compiler will infer the type that is needed
	}
	
	/**
	 * 用于判断一个元素数组中是包含指定的元素
	 */
	public static <T> boolean contain(T[] elements, T element) {
		for (T e : elements) {
			if (e.equals(element)) {
				return true;
			}
		}
		return false;
	}
}
