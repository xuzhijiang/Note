package org.java.core.base.serialization;

import org.junit.Test;
import java.io.*;

public class SerializationTest {

	@Test
	public void basicSerializationTest() throws IOException, ClassNotFoundException {
		String fileName="employee.ser";
		Employee emp = new Employee("xzj", 10, 100000, "password");

		// 把一个对象序列化到文件
		SerializationUtils.serialize(emp, fileName);

		// 从文件反序列化
		Employee empNew = (Employee)SerializationUtils.deserialize(fileName);

		// 由于salary是一个transient变量，它的值没有被保存到文件，因此没有在新对象中找到
		// 类似的静态变量也没有被序列化，因为它属于类，而不是对象。
		System.out.println(empNew);

		File file = new File(fileName);
		file.delete();
	}

	@Test
	public void proxySerializationTest() throws IOException, ClassNotFoundException {
		String fileName = "Data.ser";
		Data data = new Data("data");

		// 把一个对象序列化到文件
		SerializationUtils.serialize(data, fileName);

		// 从文件反序列化
		Data dataNew = (Data)SerializationUtils.deserialize(fileName);

		// 由于salary是一个transient变量，它的值没有被保存到文件，因此没有在新对象中找到
		// 类似的静态变量也没有被序列化，因为它属于类，而不是对象。
		System.out.println(dataNew);

		File file = new File(fileName);
		file.delete();
	}
}

/**
 * Now uncomment the password variable and it's getter-setter
 * methods from Employee class and run it. You will get below exception;
 *
 * The reason is clear that serialVersionUID of the
 * previous class and new class are different. Actually
 * if the class doesn't define serialVersionUID, it's
 * getting calculated automatically and assigned to the
 * class. Java uses class variables, methods, class name,
 * package etc to generate this unique long number.
 *
 * If you are working with any IDE, you will automatically get a
 * warning that The serializable class Employee does not
 * declare a static final serialVersionUID field of type long.
 */
/**
 * 先把password以及它的getter和setter方法都注释掉，然后序列化后，然后再
 * 取消注释password以及getter和setter方法，反序列化的时候会的到异常.
 *
 * 原因很明显：就是先前类和新类的serialVersionUID是不同的，实际上，如果
 * 类没有定义serialVersionUID,会自动计算一个serialVersionUID值给这个类，
 * Java使用类的变量，方法名，类名，包名等来生成这个唯一的long类型的数字.
 */
class Employee implements Serializable{ // 注意这个类不能是静态私有的，否则反序列化有问题.

	private static final long serialVersionUID = 6115687027597651376L;

	private static final String SECRET_STR = "!x*13)$ln&(i$z.i";

	private String name;

	private int id;

	private transient int salary;

	private transient String password;

	@Override
	public String toString() {
		return "Employee{" +
				"name='" + name + '\'' +
				", id=" + id +
				", salary=" + salary +
				", password='" + password + '\'' +
				'}';
	}

	public Employee(String name, int id, int salary, String password) {
		this.name = name;
		this.id = id;
		this.salary = salary;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

/**
 *  有时我们想隐藏(obscure)对象数据以保持其完整性(integrity)。
 *  我们可以通过实现java.io.Externalizable接口来实现这一点，
 *  提供writeExternal（）和readExternal（）方法的实现，以便在序列化过程中使用。
 */
class Person implements Externalizable{

	private int id;
	private String name;
	private String gender;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// 把它转换成流之前改变了字段的值，读取的时候又反转了更改，
		// 通过这种方式我们可以保持某些数据的完整性，
		// 那么哪一个更适合用于java中的序列化。 实际上最好使用Serializable接口
		out.writeInt(id);
		out.writeObject(name+"xyz");
		out.writeObject("abc"+gender);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// 读的顺序要和写的顺序一致
		id = in.readInt();
		name = (String) in.readObject();
		if(!name.endsWith("xyz")){
			throw new IOException("corrupted data");
		}
		name = name.substring(0, name.length() - 3);
		gender = (String) in.readObject();
		if(!gender.startsWith("abc")){
			throw new IOException("corrupted data");
		}
		gender = gender.substring(3);
	}

	@Override
	public String toString(){
		return "Person{id=" + id + ",name=" + name + ",gender=" + gender + "}";
	}

}