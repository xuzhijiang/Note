package org.java.core.base.serialization.ObjectInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.java.core.base.serialization.Employee;

/**
 * 转换输入流(file)为对象的过程叫做反序列化,ObjectInputStream的
 * 目的就是提供给我们转换输入流为对象的一种方式,
 */
public class ObjectInputStreamExample {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		FileInputStream is = new FileInputStream("EmployeeObject.ser");
		ObjectInputStream ois = new ObjectInputStream(is);
		Employee emp = (Employee) ois.readObject();//We have to do class casting to convert Object to actual class
		ois.close();
		is.close();
		System.out.println(emp.toString());
	}
}
