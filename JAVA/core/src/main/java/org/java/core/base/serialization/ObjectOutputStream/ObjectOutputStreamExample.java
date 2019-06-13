package org.java.core.base.serialization.ObjectOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.java.core.base.serialization.Employee;

/**
 * convert an object to output stream,把对象转换成流的过程叫做
 * 序列化，一旦一个对象转换成了流，那么就可以保存到文件or数据库，就可以通过
 * 网络传输or在socket连接中使用了，
 * @author xu
 *
 */
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
