package org.java.core.base.serialization;

import org.junit.Test;

import java.io.*;

public class SerializationTest {

	@Test
	public void basicSerializationTest() throws IOException, ClassNotFoundException {
		String fileName="employee.ser";
		Employee emp = new Employee("xzj", 10, 100000, "password");

		// 把一個對象序列化到文件
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(emp);
		oos.close();

		// 從文件反序列化
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Employee empNew = (Employee)ois.readObject();
		ois.close();

		// 由於salary是一個transient的變量,它的值沒有被保存到文件，因此沒有在新對象中找到
		// 類似的靜態變量也沒有被序列化，因爲它屬於類，而不是對象.
		System.out.println(empNew);
	}
}
