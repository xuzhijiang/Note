package org.java.core.base.serialization.inheritance;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Sometimes we need to extend a class that doesn��t implement 
 * Serializable interface. If we rely on the automatic serialization 
 * behavior and the superclass has some state, then they will not 
 * be converted to stream and hence not retrieved later on.
 * <p><br>
 * ��Щʱ��������Ҫȥ��չһ���࣬�����û��ʵ��Serializable�ӿ�(a third-party class that we can��t changeStr.)
 * ������������Զ����л���Ϊ(ָ����Ҫ���л�һ�������ʵ��Serializable�ӿ�,���ǵ�������û��ʵ��)���������������һЩ״̬,
 * ��ô������ǾͲ��ܹ���ת����Ϊ���������֮��Ͳ����Ա�������.
 * <p><br>
 * This is one place, where readObject() and writeObject() 
 * methods really help. By providing their implementation, 
 * we can save the super class state to the stream and then 
 * retrieve it later on. 
 * <p><br>
 * �������readObject()��writeObject()���������а����ĵط���ͨ���ṩ���ǵ�ʵ�֣����ǿ���
 * �Ѹ���� ״̬���浽���У�Ȼ����֮�������.
 * <p><br>
 * So in this way, we can serialize super class state even 
 * though it��s not implementing Serializable interface. 
 * This strategy comes handy when the super class is a third-party 
 * class that we can��t changeStr.
 * ��ˣ�ͨ�����ַ�ʽ�����ǿ������л�����״̬����ʹ��û��ʵ��Serializable�ӿڡ� 
 * ���������������޷��ı�ĵ�������ʱ�����ֲ��Ժܷ��㡣
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
	
	//ע��д�Ͷ���˳��Ӧ��һ�£����ǿ��Լ�һЩ��д���߼�����ȷ�����ݰ�ȫ
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

	//������ӿ���Ϊ��������֤����ȷ�����������Բ��ܵ��˺�.
	@Override
	public void validateObject() throws InvalidObjectException {
		//validate the object here
		if(name == null || "".equals(name)) throw new InvalidObjectException("name can't be null or empty");
		if(getId() <= 0){
			throw new InvalidObjectException("Id can't be negative or zero");
		}
	}
}
