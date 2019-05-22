package org.java.core.base.reflection.part1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class ReflectionForConstructors {
	
	public static void main(String[] args) {
		
		//Reflection API provides methods to get the constructors of a class to analyze 
		//and we can create new instances of class by invoking the constructor. 
		//We have already learned how to get all the public constructors.

		//Get Public Constructor
		//We can use getConstructor() method on the class representation of 
		//object to get specific public constructor. Below JdbcQuickStartExample shows how
		//to get the constructor of ConcreteClass defined above and the 
		//no-argument constructor of HashMap. It also shows how to get the array 
		//of parameter types for the constructor.
		try {
			Constructor<?> constructor = Class.forName("org.java.core.base.reflection.part1.ConcreteClass").getConstructor(int.class);
			System.out.println(Arrays.toString(constructor.getParameterTypes()));

			Constructor<?> hashMapConstructor = Class.forName("java.util.HashMap").getConstructor(null);
			System.out.println(Arrays.toString(hashMapConstructor.getParameterTypes())); // prints "[]"
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Instantiate Object using Constructor(用构造器去初始化对象的)
		
		//We can use newInstance() method on the constructor object 
		//to instantiate a new instance of the class.(我们用构造器对象的newInstance方法去初始化一个类的新实例)
		
		//Since we use reflection when we don’t have the classes information at compile time, 
		//we can assign it to Object and then further use reflection to access it’s 
		//fields and invoke it’s methods.
		//因为我们用了反射，我们在编译时没有关于类的信息,
		
		try {
			Constructor<?> constructor = Class.forName("org.java.core.base.reflection.part1.ConcreteClass").getConstructor(int.class);
			//getting constructor parameters
			System.out.println(Arrays.toString(constructor.getParameterTypes())); // prints "[int]"
			Object myObj = constructor.newInstance(10);
			Method myObjMethod = myObj.getClass().getMethod("method1", null);
			myObjMethod.invoke(myObj, null); //prints "Method1 impl."

			Constructor<?> hashMapConstructor = Class.forName("java.util.HashMap").getConstructor(null);
			System.out.println(Arrays.toString(hashMapConstructor.getParameterTypes())); // prints "[]"
			HashMap<String,String> myMap = (HashMap<String,String>) hashMapConstructor.newInstance(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
