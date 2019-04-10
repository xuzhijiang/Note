package org.java.core.base.reflection.part1;

public class GetSuperClassExample {
	public static void main(String[] args){
		try {
			Class<?> superClass = Class.forName("org.java.core.base.reflection.part1.ConcreteClass").getSuperclass();
			System.out.println(superClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(Object.class.getSuperclass());
		System.out.println(boolean.class.getSuperclass());
		System.out.println(void.class.getSuperclass());
		System.out.println(void.class);
		System.out.println(void.class instanceof Class);
		System.out.println(Object.class instanceof Class);
		
		System.out.println(String[][].class);
		System.out.println(String[][].class.getSuperclass());// prints "class java.lang.Object"
		
		
	}
}
