package org.java.core.base.string;

/**
 * 一句话总结：
 *
 * 对象类型永远传引用；
 * 基本类型传值。
 */
public class ReferenceValuePass {
	public static void main(String[] args) {
		String str = "abc";
		System.out.println("1111111: " + str.hashCode());
		changeStr(str);
		System.out.println("444444:" + str);
		System.out.println("555555:" + str.hashCode());
		
		System.out.println("-----------------------");
		
		StringObject so = new StringObject();
		so.name = "old name";
		System.out.println(so.hashCode());
		changeStr(so);
		System.out.println(so.name);
	}
	
	/**
	 * 形参str和传入的str都指向同一个String对象
	 */
	public static void changeStr(String str) {
		str = "def";
		System.out.println("222222:" + str);
		System.out.println("333333:" + str.hashCode());
	}
	
	/**
	 * 形参so和传入的so都指向同一个StringObject对象
	 */
	public static void changeStr(StringObject so) {
		System.out.println(so.hashCode());
		so = new StringObject();
		so.name = "new name";
		System.out.println(so.hashCode());
	}

	private static class StringObject
	{
		String name;
	}
}

