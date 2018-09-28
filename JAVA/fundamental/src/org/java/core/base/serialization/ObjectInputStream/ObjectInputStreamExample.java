package org.java.core.base.serialization.ObjectInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.java.core.base.serialization.Employee;

public class ObjectInputStreamExample {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		FileInputStream is = new FileInputStream("EmployeeObject.ser");
		ObjectInputStream ois = new ObjectInputStream(is);
		Employee emp = (Employee) ois.readObject();
		ois.close();
		is.close();
		System.out.println(emp.toString());
	}
}
