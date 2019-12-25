package org.java.core.base;

/**
 * 内部类和静态内部类如何被实例化?
 */
public class InnerClassDemo {

	static class StaticInnerClass{}

	class InnerClass{}

	public static void main(String[] args) {
		InnerClassDemo.InnerClass innerClass = new InnerClassDemo().new InnerClass();
		InnerClassDemo.StaticInnerClass staticInnerClass = new InnerClassDemo.StaticInnerClass();
	}
}
