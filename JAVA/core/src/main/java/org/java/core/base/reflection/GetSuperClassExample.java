package org.java.core.base.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;

//如果方法是静态的，我们可以传递null作为对象类型
public class GetSuperClassExample {
	public static void main(String[] args) throws NoSuchMethodException {
		System.out.println(Object.class.getSuperclass());
		System.out.println(Object.class instanceof Class);
		System.out.println(String[].class.getSuperclass());

		// For primitive types, wrapper classes and arrays
		Class<?> booleanClass = boolean.class;
		System.out.println(booleanClass);
		System.out.println(Boolean.class);

		Class<?> DoubleClass = Double.TYPE;
		System.out.println(DoubleClass.getName());
		System.out.println(double.class);
		System.out.println(Double.class);

		System.out.println("-----------------------------------");
		try {
			Class<?> clazz = HashMap.class;
			Method method = clazz.getMethod("put", Object.class, Object.class);
			System.out.println(Arrays.toString(method.getParameterTypes()));
			System.out.println(method.getReturnType());
			System.out.println(Modifier.toString(method.getModifiers()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("-----------------------------------");

		try {
			Field field = Class.forName("org.java.core.base.reflection.ConcreteClass").getField("interfaceInt");
			// 获取声明这个field的Class对象
			Class<?> fieldClass = field.getDeclaringClass();
			System.out.println(fieldClass.getName());
			// 获取这个field的类型
			System.out.println(field.getType().getName());
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("-----------------------------------");
		try{
			Class<?> clazz = Class.forName("org.java.core.base.reflection.ConcreteClass");
			Field field = clazz.getField("publicInt");
			Constructor<?> constructor = clazz.getConstructor(int.class);
			Object obj = constructor.newInstance(100);
			field.setAccessible(true);
			System.out.println(field.get(obj));
			field.set(obj, -100);
			System.out.println(field.get(obj));
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("-----------------------------------");

		Class<?> concreteClass = ConcreteClass.class;
		// 获取Class对象内部包含的public的字节码对象,也就是公有的,内部类/内部接口/内部Enum
		Class<?>[] classes = concreteClass.getClasses();
		System.out.println(Arrays.toString(classes));

		System.out.println("-----------------------------------");

		try {
			// getting all of the classes, interfaces, and enums
			// that are explicitly declared in ConcreteClass
			//Note: The returned array doesn't include classes declared in inherited classes and interfaces.
			Class<?> clazz = ConcreteClass.class;
			// 获取所有自己声明的内部类,不包括继承来的以及实现的接口的.
			Class<?>[] explicitClasses = clazz.getDeclaredClasses();
			for (int i = 0; i < explicitClasses.length; i++) {
				System.out.println(explicitClasses[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------------------");

		try {
			Class<?> innerClass = Class.forName("org.java.core.base.reflection.ConcreteClass$ConcreteClassDefaultClass");
			// 获取声明内部类的那个类
			System.out.println(innerClass.getDeclaringClass().getCanonicalName());
			System.out.println(innerClass.getEnclosingClass().getCanonicalName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------------------");

		try {
			//Getting Package Name
			System.out.println(Class.forName("org.java.core.base.reflection.BaseInterface").getPackage().getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------------------");

		try {
			//Getting Class Modifiers
			System.out.println("xxxxxxxxxx" + Modifier.toString(concreteClass.getModifiers()));
			System.out.println(Modifier.toString(Class.forName("org.java.core.base.reflection.BaseInterface").getModifiers()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------------------");

		//Getting Type Parameters (generics)
//		The type parameters are returned in the same order as declared.
		try {
			TypeVariable<?>[] typeParameters = HashMap.class.getTypeParameters();
			for(TypeVariable t : typeParameters) {
				System.out.println(t.getName() + ",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------------------");

		//Get Implemented Interfaces
		//etGenericInterfaces() method returns the array of
		//interfaces implemented by the class with generic type
		//information. We can also use getInterfaces() to get the
		//class representation of all the implemented interfaces.
		try {
			Type[] interfaces = Class.forName("java.util.HashMap").getGenericInterfaces();
			System.out.println("xxxxxxxxxx" + Arrays.toString(interfaces));
			System.out.println("xxxxxxxxxx" + Arrays.toString(HashMap.class.getInterfaces()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//Get All Public Methods
		//getMethods() method returns the array of public methods
		//of the Class including public methods of it's superclasses
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

		System.out.println("------------");
		Annotation[] annotations = concreteClass.getAnnotations();
		System.out.println(Arrays.toString(annotations));
	}
}

interface BaseInterface {
	int interfaceInt = 0;
	void method1();
	int method2(String str);
}

class BaseClass {

	public int baseInt;

	private static void method3(){
		System.out.println("Method3");
	}

	public int method4(){
		System.out.println("Method4");
		return 0;
	}

	public static int method5(){
		System.out.println("Method5");
		return 0;
	}

	void method6(){
		System.out.println("Method6");
	}

	//inner public class
	public class BaseClassInnerClass{}

	//member public enum
	public enum BaseClassMemberEnum{}
}

class ConcreteClass extends BaseClass implements BaseInterface{

	public int publicInt;

	private String privateString = "private string";

	protected boolean protectedBoolean;

	Object defaultObject;

	public ConcreteClass(int i){
		this.publicInt = i;
	}

	@Override
	public void method1() {
		System.out.println("Method1 impl.");
	}

	@Override
	public int method2(String str) {
		System.out.println("Method2 impl.");
		return 0;
	}

	@Override
	public int method4(){
		System.out.println("Method4 overriden.");
		return 0;
	}

	public int method5(int i){
		System.out.println("Method4 overriden.");
		return 0;
	}

	// inner class
	public class ConcreteClassPublicClass{}
	private class ConcreteClassPrivateClass{}
	protected class ConcreteClassProtectedClass{}
	class ConcreteClassDefaultClass{}

	//member enum
	enum ConcreteClassDefaultEnum{}
	public enum ConcreteClassPublicEnum{}

	//member interface
	public interface ConcreteClassPublicInterface{}
}
