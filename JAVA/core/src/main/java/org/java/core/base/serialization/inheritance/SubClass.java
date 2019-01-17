package org.java.core.base.serialization.inheritance;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Sometimes we need to extend a class that doesn’t implement 
 * Serializable interface. If we rely on the automatic serialization 
 * behavior and the superclass has some state, then they will not 
 * be converted to stream and hence not retrieved later on.
 * <p><br>
 * 有些时候我们需要去扩展一个类，这个类没有实现Serializable接口(a third-party class that we can’t change.) 
 * 如果我们依赖自动序列化行为(指的是要序列化一个类必须实现Serializable接口,但是第三方类没有实现)，并且这个超类有一些状态,
 * 那么这个他们就不能够被转化成为流，因此在之后就不可以被检索到.
 * <p><br>
 * This is one place, where readObject() and writeObject() 
 * methods really help. By providing their implementation, 
 * we can save the super class state to the stream and then 
 * retrieve it later on. 
 * <p><br>
 * 这个就是readObject()和writeObject()方法真正有帮助的地方，通过提供他们的实现，我们可以
 * 把父类的 状态保存到流中，然后在之后检索它.
 * <p><br>
 * So in this way, we can serialize super class state even 
 * though it’s not implementing Serializable interface. 
 * This strategy comes handy when the super class is a third-party 
 * class that we can’t change.
 * 因此，通过这种方式，我们可以序列化超类状态，即使它没有实现Serializable接口。 
 * 当超级类是我们无法改变的第三方类时，这种策略很方便。
 */
public class SubClass extends SuperClass implements Serializable, ObjectInputValidation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6401893717049176870L;
	
	private String name;
	
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
	
	//注意写和读的顺序应该一致，我们可以加一些读写的逻辑，以确保数据安全
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
		ois.defaultReadObject();
		//notice the order of read and write should be same
		setId(ois.readInt());
		setValue((String) ois.readObject());
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException{
		oos.defaultWriteObject();
		
		oos.writeInt(getId());
		oos.writeObject(getValue());
	}

	//加这个接口是为了数据验证，以确保数据完整性不受到伤害.
	@Override
	public void validateObject() throws InvalidObjectException {
		//validate the object here
		if(name == null || "".equals(name)) throw new InvalidObjectException("name can't be null or empty");
		if(getId() <= 0){
			throw new InvalidObjectException("Id can't be negative or zero");
		}
	}
}
