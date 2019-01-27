package org.java.core.base.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ExternalizationTest {
	public static void main(String[] args){
		
		String fileName = "person.ser";
		Person person = new Person();
		person.setId(1);
		person.setName("xzj");
		person.setGender("Male");
		
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(person);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Person p = (Person) ois.readObject();
			ois.close();
			System.out.println("Person Object read=" + p);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}