package org.java.core.base.reflection;

import java.lang.reflect.Field;

/**
 * 作用域越小越好
 */
public class ReflectionForField {
	public static void main(String[] args) {
		//Get Public Field
		//In last section, we saw how to get the list of all the 
		//public fields of a class. Reflection API also provides 
		//method to get specific public field (得到指定的公共的field)of a class through getField() 
		//method. This method look for the field in the specified class 
		//reference and then in the super interfaces and then in the super classes.
		//(此方法在指定的类引用中查找字段，然后在超级接口中查找，然后在超类中查找。)
		try {
			Field field = Class.forName("org.java.core.base.reflection.ConcreteClass").getField("interfaceInt");
			System.out.println(field.getName());
			//Above call will return the field from BaseInterface 
			//that is implemented by ConcreteClass. If there is no field
			//found then it throws NoSuchFieldException.
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Field Declaring Class
		//We can use getDeclaringClass() of field object to get the class declaring the field.

		try {
			Field field = Class.forName("org.java.core.base.reflection.ConcreteClass").getField("interfaceInt");
			Class<?> fieldClass = field.getDeclaringClass();
			System.out.println(fieldClass.getCanonicalName());
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Get Field Type
		//getType() method returns the Class object for the 
		//declared field type, if field is primitive type, it returns the wrapper class object.

		try{
			Field field = Class.forName("org.java.core.base.reflection.ConcreteClass").getField("publicInt");
			Class<?> fieldType = field.getType();
			System.out.println(fieldType.getCanonicalName()); //prints int
		}catch(Exception e){
			e.printStackTrace();
		}

		//Get/Set Public Field Value
		//We can get and set the value of a field in an Object using reflection.
		//我们可以用反射get和set对象中的字段的值
		try{
			Field field = Class.forName("org.java.core.base.reflection.ConcreteClass").getField("publicInt");
			ConcreteClass obj = new ConcreteClass(5);
			System.out.println(field.get(obj)); //prints 5
			field.setInt(obj, 10); //setting field value to 10 in object
			System.out.println(field.get(obj)); //prints 10
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//get() method return Object, so if field is primitive type, 
		//it returns the corresponsing Wrapper Class. If the field is static, 
		//we can pass Object as null in get() method.

		//There are several set*() methods to set Object to the field or 
		//set different types of primitive types to the field. We can get the 
		//type of field and then invoke correct function to set the field value 
		//correctly. If the field is final, the set() methods throw 
		//java.lang.IllegalAccessException.


		//Get/Set Private Field Value
		//We know that private fields and methods can’t be accessible outside 
		//of the class but using reflection we can get/set the private field 
		//value by turning off the java access check for field modifiers.(通过关闭字段修饰符的java访问检查)

		try {
			Field privateField = Class.forName("org.java.core.base.reflection.ConcreteClass").getDeclaredField("privateString");
			//turning off access check with below method call
			privateField.setAccessible(true);
			ConcreteClass objTest = new ConcreteClass(1);
			System.out.println(privateField.get(objTest)); // prints "private string"
			privateField.set(objTest, "private string updated");
			System.out.println(privateField.get(objTest)); //prints "private string updated"
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
