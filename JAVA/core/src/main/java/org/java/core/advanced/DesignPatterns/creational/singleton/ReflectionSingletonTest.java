package org.java.core.advanced.DesignPatterns.creational.singleton;

import java.lang.reflect.Constructor;

/**
 * Reflection can be used to destroy all the above singleton implementation approaches.
 * <p><br>
 * When you run the above test class, you will notice that hashCode of
 *  both the instances are not same that destroys the singleton pattern. 
 *  Reflection is very powerful and used in a lot of frameworks like Spring and Hibernate
 */
public class ReflectionSingletonTest {
	public static void main(String[] args) {
		EagerInitializedSingleton instanceOne = EagerInitializedSingleton.getInstance();

		EagerInitializedSingleton instanceTwo = null;

		try {
				Constructor[] constructors = EagerInitializedSingleton.class.getDeclaredConstructors();
				for(Constructor constructor : constructors) {
					constructor.setAccessible(true);
				instanceTwo = (EagerInitializedSingleton) constructor.newInstance();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(instanceOne.hashCode());
		System.out.println(instanceTwo.hashCode());
	}
}
