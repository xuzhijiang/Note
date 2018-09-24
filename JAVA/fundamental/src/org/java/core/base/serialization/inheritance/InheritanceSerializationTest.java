package org.java.core.base.serialization.inheritance;

import java.io.IOException;

import org.java.core.base.serialization.SerializationUtil;

public class InheritanceSerializationTest {
	public static void main(String[] args){
		String fileName = "subclss.ser";
		
		SubClass subClass = new SubClass();
		subClass.setId(10);
		subClass.setValue("Data");
		subClass.setName("XZJ");
		
		try {
			SerializationUtil.serialize(subClass, fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			SubClass subNew = (SubClass) SerializationUtil.deserialize(fileName);
			System.out.println("SubClass Read=" + subNew);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
