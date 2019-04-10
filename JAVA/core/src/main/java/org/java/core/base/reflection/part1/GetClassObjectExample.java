package org.java.core.base.reflection.part1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * Java�����ṩ��ȥ���Ӻ��޸�Ӧ������ʱ��Ϊ���������÷��䣬���ǿ��Լ��һ���࣬�ӿڣ�ö�٣�������ʱ�õ����ǵĽṹ��
 * �������ֶ���Ϣ��ʹ���ڱ���ʱ�ǲ����Է��ʵģ����ǿ����÷���ʵ����һ�����󣬵������ķ������ı������ֶ�ֵ,Ҳ���Ƿ���������������ʱ.
 * <p><br>
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
		Class<?> concreteClass = ConcreteClass.class;// ?�ű�ʾClass����κ�һ��ʵ����Ҳ���κ�һ������

		// using getClass() method of object
		concreteClass = new ConcreteClass(5).getClass();

		// java.lang.Class.forName(String fullyClassifiedClassName)
		try {
			// below method is used most of the times in frameworks like JUnit
			// Spring dependency injection, Tomcat web container
			// Eclipse auto completion of method names, hibernate, Struts2 etc.
			// because ConcreteClass is not available at compile time
			concreteClass = Class.forName("org.java.core.base.reflection.part1.ConcreteClass");
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
		// that are explicitly declared(��ʾ����) in ConcreteClass
		//Note: The returned array doesn��t include classes declared in inherited classes and interfaces.
		Class<?>[] explicitClasses;
		try {
			explicitClasses = Class.forName("org.java.core.base.reflection.part1.ConcreteClass").getDeclaredClasses();
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
			System.out.println(Class.forName("org.java.core.base.reflection.part1.BaseInterface").getPackage().getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Getting Class Modifiers
		System.out.println(Modifier.toString(concreteClass.getModifiers()));
		try {
			System.out.println(Modifier.toString(Class.forName("org.java.core.base.reflection.part1.BaseInterface").getModifiers()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Getting Type Parameters (generics)
//		The type parameters are returned in the same order as declared.
		try {
			TypeVariable<?>[] typeParameters = Class.forName("java.util.HashMap").getTypeParameters();
			for(TypeVariable t : typeParameters) {
				System.out.println(t.getName() + ",");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Get Implemented Interfaces
		//etGenericInterfaces() method returns the array of 
		//interfaces implemented by the class with generic type 
		//information. We can also use getInterfaces() to get the
		//class representation of all the implemented interfaces.
		try {
			Type[] interfaces = Class.forName("java.util.HashMap").getGenericInterfaces();
			System.out.println(Arrays.toString(interfaces));
			System.out.println(Arrays.toString(Class.forName("java.util.HashMap").getInterfaces()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Get All Public Methods
		//getMethods() method returns the array of public methods 
		//of the Class including public methods of it��s superclasses
		//and super interfaces.
		Method[] publicMethods = concreteClass.getMethods();
		System.out.println(Arrays.toString(publicMethods));
		
		//Get All Public Constructors
		//getConstructors() method returns the list of public
		//constructors of the class reference of object.
		
		Constructor<?>[] publicConstructors = concreteClass.getConstructors();
		System.out.println(Arrays.toString(publicConstructors));
		
		//Get All Public Fields
		//getFields() method returns the array of public fields 
		//of the class including public fields of it��s super classes and super interfaces.
		System.out.println("------------");
		Field[] publicFields = concreteClass.getFields();
		System.out.println(Arrays.toString(publicFields));
		
		//Get All Annotations
		//getAnnotations() method returns all the annotations for 
		//the element, we can use it with class, fields and methods also. 
		//Note that only annotations available with reflection are with retention 
		//policy of RUNTIME, ����Ҳ���Խ������࣬�ֶκͷ���һ��ʹ�á� ��ע�⣬ֻ�д������ע�Ͳ���ʹ��RUNTIME�ı������ԣ�
		System.out.println("------------");
		Annotation[] annotations = concreteClass.getAnnotations();
		System.out.println(Arrays.toString(annotations));
		
	}
}
