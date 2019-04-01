package org.java.core.base.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReflectionForMethod {
	
	public static void main(String[] args) {
		
		//We can use getMethod() to get a public method of class,
		//we need to pass the method name and parameter types of the method. 
		//If the method is not found in the class, reflection API looks 
		//for the method in superclass.
		//我们可以用getMethod()得到类的public方法.
		
		//In below example, I am getting put() method of HashMap 
		//using reflection. The example also shows how to get the 
		//parameter types, method modifiers and return type of a method.
		//我用反射得到HashMap的put方法，这个例子也展示了怎么去得到这个方法的参数 类型，方法修饰符以及返回类型
		
		// Get Public Method
		try {
			Method method = Class.forName("java.util.HashMap").getMethod("put", Object.class, Object.class);
			// get method parameter types, prints "[class java.lang.Object, class
			// java.lang.Object]"
			System.out.println(Arrays.toString(method.getParameterTypes()));
			// get method return type, return "class java.lang.Object", class reference for
			// void
			System.out.println(method.getReturnType());
			// get method modifiers
			System.out.println(Modifier.toString(method.getModifiers())); // prints "public"
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Invoking Public Method
		//We can use invoke() method of Method object to invoke a 
		//method, in below example code I am invoking put method on HashMap using reflection.
		//我们可以用Method对象的invoke()方法去调用方法
		//If the method is static, we can pass NULL as object argument.
		//如果方法是静态的，我们可以传递null作为对象类型
		try {
			Method method = Class.forName("java.util.HashMap").getMethod("put", Object.class, Object.class);
			Map<String, String> hm = new HashMap<>();
			method.invoke(hm, "key", "value");
			System.out.println(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Invoking Private Methods
		//We can use getDeclaredMethod() to get the private method and 
		//then turn off the access check to invoke it, below example shows
		//how we can invoke method3() of BaseClass that is static and have no parameters.
		//用getDeclardMethod()得到私有方法，然后关闭访问检查，然后调用它
		
		try {
			Method method = Class.forName("org.java.core.base.reflection.BaseClass").getDeclaredMethod("method3", null);
			method.setAccessible(true);
			method.invoke(null, null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
