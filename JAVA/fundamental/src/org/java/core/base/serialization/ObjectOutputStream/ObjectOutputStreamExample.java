package org.java.core.base.serialization.ObjectOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.java.core.base.serialization.Employee;

public class ObjectOutputStreamExample {
	
	public static void main(String[] args) {
		Employee emp = new Employee();

		emp.setId(35);
		emp.setName("ZXJ");
		System.out.println(emp);
		
		try {
			FileOutputStream fos = new FileOutputStream("EmployeeObject.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// write object to file
			oos.writeObject(emp);
			System.out.println("Done");
			// closing resources
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
