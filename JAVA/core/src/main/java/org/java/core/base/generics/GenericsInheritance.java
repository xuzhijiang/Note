package org.java.core.base.generics;

/**
 * Java泛型和继承:
 * 
 * 我们知道，如果A是B的子类，Java继承允许我们将A的实例对象 分配给另一个类型是B的变量.
 *
 * 我们可能认为任何泛型类型的A都可以分配给泛型B，但事实并非如此。
 * 
 * We are not allowed to assign MyClass<String> variable 
 * to MyClass<Object> variable because they are not related,
 *  in fact MyClass<T> parent is Object.
 *
 */
public class GenericsInheritance {

	public static void main(String[] args) {
		String str = "abc";
		Object obj = new Object();
		obj=str; // works because String is-a Object, inheritance in java
		
		MyClass<String> myClass1 = new MyClass<String>();
		MyClass<Object> myClass2 = new MyClass<Object>();
		
		//myClass2=myClass1; // compilation error since MyClass<String> is not a MyClass<Object>
		obj = myClass1; // MyClass<T> parent is Object
	}
	
	public static class MyClass<T>{}

}
