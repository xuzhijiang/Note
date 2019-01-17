package org.java.core.advanced.DesignPatterns.creational.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class SingletonSerializedTest {
	public static void main(String[] args) {
		SerializedSingleton instanceOne = SerializedSingleton.getInstance();
		try {
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream("filename.ser"));
			out.writeObject(instanceOne);
			out.close();
			
			//deserialize from file to object
			ObjectInput in = new ObjectInputStream(new FileInputStream("filename.ser"));
			SerializedSingleton instanceTwo = (SerializedSingleton) in.readObject();
			in.close();
			
			System.out.println(instanceOne.hashCode());
			System.out.println(instanceTwo.hashCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
