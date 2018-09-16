package org.java.core.base.reflection;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * getCanonicalName() returns the canonical name of the underlying class. Notice
 * that java.lang.Class uses Generics, it helps frameworks in making sure that
 * the Class retrieved is subclass of framework Base Class. Check out Java
 * Generics Tutorial to learn about generics and its wildcards.
 */
public class GetClassObjectExample {
	public static void main(String[] args) {
		// Get class of an object using three methods
		// For primitive types and arrays, we can use static variable class.
		// Wrapper classes provide another static variable TYPE to get the
		// class.

		// through static variable class.
		Class<?> concreteClass = ConcreteClass.class;// ?号表示Class类的任何一个实例，也即任何一个类名

		// using getClass() method of object
		concreteClass = new ConcreteClass(5).getClass();

		// java.lang.Class.forName(String fullyClassifiedClassName)
		try {
			// below method is used most of the times in frameworks like JUnit
			// Spring dependency injection, Tomcat web container
			// Eclipse auto completion of method names, hibernate, Struts2 etc.
			// because ConcreteClass is not available at compile time
			concreteClass = Class.forName("org.java.core.base.reflection.ConcreteClass");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(concreteClass.getCanonicalName());//

		// Get public memeber class
		// getClasses() method of a Class representation of object returns
		// an array containing Class objects representing all the public
		// classes,
		// interfaces and enums that are members of the class represented by
		// this Class object. This includes public class and interface members
		// inherited from superclasses and public class and interface members
		// declared
		// by the class. This method returns an array of length 0 if this Class
		// object
		// has no public member classes or interfaces or if this Class object
		// represents a primitive type, an array class, or void.
		Class<?>[] classes = concreteClass.getClasses();
		//System.out.println(Arrays.toString(classes));
		for (int i = 0; i < classes.length; i++) {
			System.out.println(classes[i]);
		}

		// For primitive types, wrapper classes and arrays
		Class<?> booleanClass = boolean.class;
		System.out.println(booleanClass.getCanonicalName());

		Class<?> cDouble = Double.TYPE;
		System.out.println(cDouble.getCanonicalName());

		// Class<?> cDoubleArray;
		try {
			Class<?> cDoubleArray = Class.forName("[D");
			System.out.println(cDoubleArray.getCanonicalName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Class<?> twoDStringArray = String[][].class;
		System.out.println(twoDStringArray.getCanonicalName());

		// getting all of the classes, interfaces, and enums
		// that are explicitly declared(显示声明) in ConcreteClass
		//Note: The returned array doesn’t include classes declared in inherited classes and interfaces.
		Class<?>[] explicitClasses;
		try {
			explicitClasses = Class.forName("org.java.core.base.reflection.ConcreteClass").getDeclaredClasses();
			// prints [class
			// com.journaldev.reflection.ConcreteClass$ConcreteClassDefaultClass,
			// class
			// com.journaldev.reflection.ConcreteClass$ConcreteClassDefaultEnum,
			// class
			// com.journaldev.reflection.ConcreteClass$ConcreteClassPrivateClass,
			// class
			// com.journaldev.reflection.ConcreteClass$ConcreteClassProtectedClass,
			// class
			// com.journaldev.reflection.ConcreteClass$ConcreteClassPublicClass,
			// class
			// com.journaldev.reflection.ConcreteClass$ConcreteClassPublicEnum,
			// interface
			// com.journaldev.reflection.ConcreteClass$ConcreteClassPublicInterface]
			//System.out.println(Arrays.toString(explicitClasses));
			for (int i = 0; i < explicitClasses.length; i++) {
				System.out.println(explicitClasses[i]);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Get Declaring Class
		//getDeclaringClass() method returns the Class object representing 
		//the class in which it was declared.

		Class<?> innerClass;
		try {
			innerClass = Class.forName("org.java.core.base.reflection.ConcreteClass$ConcreteClassDefaultClass");
			//prints com.journaldev.reflection.ConcreteClass
			System.out.println(innerClass.getDeclaringClass().getCanonicalName());
			System.out.println(innerClass.getEnclosingClass().getCanonicalName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Getting Package Name
		try {
			System.out.println(Class.forName("org.java.core.base.reflection.BaseInterface").getPackage().getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Getting Class Modifiers
		System.out.println(Modifier.toString(concreteClass.getModifiers()));
		try {
			System.out.println(Modifier.toString(Class.forName("org.java.core.base.reflection.BaseInterface").getModifiers()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}
