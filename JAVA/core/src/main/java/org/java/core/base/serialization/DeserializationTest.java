package org.java.core.base.serialization;

import java.io.IOException;

/**
 * Now uncomment the ��password�� variable and it��s getter-setter 
 * methods from Employee class and run it. You will get below exception;
 * <p>
 * The reason is clear that serialVersionUID of the 
 * previous class and new class are different. Actually 
 * if the class doesn��t define serialVersionUID, it��s 
 * getting calculated automatically and assigned to the 
 * class. Java uses class variables, methods, class name, 
 * package etc to generate this unique long number. If you 
 * are working with any IDE, you will automatically get a 
 * warning that ��The serializable class Employee does not 
 * declare a static final serialVersionUID field of type long��.
 * ԭ��������֮ǰ����� serialVersionUID���µ�����ǲ�ͬ�ģ�ʵ���������û�ж������id��
 * ���Զ�����һ��id��Ȼ����������Java����������������������������������id.(�����������һ���ֶκ���Ϊ
 * serialVersionUID����û���ó������ɣ����Գ�����Զ���������һ����������һ���ֶκ�2�����id�϶���һ�������Է����л�
 * ���ɵ����ԭ���ľͲ�����)
 * <p>
 * ������serialVersionUID��Now uncomment the password field from 
 * Employee class and run the DeserializationTest program
 * and you will see that the object stream is deserialized successfully 
 * because the changeStr in Employee class is compatible with serialization process.
 * ��ΪEmployee������л������Ǽ��ݵģ�
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
