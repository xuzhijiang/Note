package org.java.core.base.serialization;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
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

	@Override
	public void validateObject() throws InvalidObjectException {
		//validate the object here
		if(name == null || "".equals(name)) throw new InvalidObjectException("name can't be null or empty");
		if(getId() <= 0){
			throw new InvalidObjectException("Id can't be negative or zero");
		}
	}
	
}
