package org.java.core.base.string;

/**
 * String的值传递和引用传递
 *
 */
public class ReferenceAndValueString {
	public static void main(String[] args) {
		String str = "abc";
		System.out.println(str.hashCode());
		change(str);
		System.out.println(str);
		System.out.println(str.hashCode());
		
		System.out.println("-----------------------");
		
		StringObject so = new StringObject();
		so.name = "old name";
		System.out.println(so.hashCode());
		change(so);
		System.out.println(so.name);
	}
	
	/**
	 * @param 形参str和传入的str都指向同一个String对象
	 */
	public static void change(String str) {
		System.out.println(str.hashCode());
		str = "def";
		System.out.println(str);
		System.out.println(str.hashCode());
	}
	
	/**
	 * @param 形参so和传入的so都指向同一个StringObject对象
	 */
	public static void change(StringObject so) {
		System.out.println(so.hashCode());
		so = new StringObject();
		so.name = "new name";
		System.out.println(so.hashCode());
	}
}

class StringObject
{
	String name;
}