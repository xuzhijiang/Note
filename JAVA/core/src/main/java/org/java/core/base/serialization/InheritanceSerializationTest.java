package org.java.core.base.serialization;

import java.io.*;

public class InheritanceSerializationTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String fileName = "subclass.ser";

		SubClass obj = new SubClass("xzj");
		obj.setId(10);
		obj.setValue("value");

        SerializationUtils.serialize(obj, fileName);

        SubClass subNew = (SubClass) SerializationUtils.deserialize(fileName);
        System.out.println("SubClass Read=" + subNew);

        File file = new File(fileName);
        file.delete();
	}

}

/**
 * Sometimes we need to extend a class that doesn't implement
 * Serializable interface. If we rely on the automatic serialization
 * behavior and the superclass has some state, then they will not
 * be converted to stream and hence not retrieved later on.
 *
 * This is one place, where readObject() and writeObject()
 * methods really help. By providing their implementation,
 * we can save the super class state to the stream and then
 * retrieve it later on.

 * So in this way, we can serialize super class state even
 * though it's not implementing Serializable interface.
 * This strategy comes handy when the super class is a third-party
 * class that we can't change it.
 *
 * 有事我们需要继承一个没有实现Serializable接口的类,如果我们依赖自动序列化行为，
 * 这个父类有一些状态，那么我们就不能将父类的状态保存到流中，然后就无法从反序列化中读取
 * 这些状态.
 *
 * 这个就是readObject() and writeObject()方法起作用的地方，通过提供他们的实现，
 * 我们可以保存父类的状态到流，并且在之后的反序列化中检索它。
 */
class SubClass extends SuperClass implements Serializable, ObjectInputValidation {

	private static final long serialVersionUID = 6401893717049176870L;

	private String name;

	public SubClass(String name) {
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	@Override
	public String toString(){
		return "SubClass{id=" + getId() + ",value=" + getValue() + ",name=" + name + "}";
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		// 注意读的顺序要和写的顺序一致.
		setId(ois.readInt());
		setValue((String) ois.readObject());
	}

	private void writeObject(ObjectOutputStream oos) throws IOException{
		oos.defaultWriteObject();
		oos.writeInt(getId());
		oos.writeObject(getValue());
	}

	@Override
	public void validateObject() throws InvalidObjectException {
		// validate the object here(在这里验证对象)
		if(name == null || "".equals(name)) throw new InvalidObjectException("name can't be null or empty");
		if(getId() <= 0){
			throw new InvalidObjectException("Id can't be negative or zero");
		}
	}
}

class SuperClass {

	private int id;
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
