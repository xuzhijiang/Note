package org.java.core.base.serialization;

import java.io.IOException;

/**
 * Now uncomment the “password” variable and it’s getter-setter 
 * methods from Employee class and run it. You will get below exception;
 * <p>
 * The reason is clear that serialVersionUID of the 
 * previous class and new class are different. Actually 
 * if the class doesn’t define serialVersionUID, it’s 
 * getting calculated automatically and assigned to the 
 * class. Java uses class variables, methods, class name, 
 * package etc to generate this unique long number. If you 
 * are working with any IDE, you will automatically get a 
 * warning that “The serializable class Employee does not 
 * declare a static final serialVersionUID field of type long”.
 * 原因很清楚，之前的类的 serialVersionUID和新的类的是不同的，实际上如果类没有定义这个id，
 * 会自动计算一个id，然后分配给它，Java用类变量，方法，类名，包名等生成这个id.(假如给类增加一个字段后，因为
 * serialVersionUID我们没有让程序生成，所以程序会自动计算生成一个，当增加一个字段后，2个类的id肯定不一样，所以反序列化
 * 生成的类和原来的就不兼容)
 * <p>
 * 增加上serialVersionUID后，Now uncomment the password field from 
 * Employee class and run the DeserializationTest program
 * and you will see that the object stream is deserialized successfully 
 * because the change in Employee class is compatible with serialization process.
 * 因为Employee类和序列化过程是兼容的，
 */
public class DeserializationTest {
	public static void main(String[] args) {
		String filename = "employee.ser";
		Employee empNew = null;
		
		try {
			empNew = (Employee) SerializationUtil.deserialize(filename);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("empNew Object: " + empNew);
	}
}
