package org.java.core.base.serialization.ProxyPattern;

import java.io.IOException;

import org.java.core.base.serialization.SerializationUtil;

public class SerializationProxyTest {
	public static void main(String[] args){
		String fileName = "data.ser";
		
		Data data = new Data("xzj");
	
		try {
			SerializationUtil.serialize(data, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Data newData = (Data) SerializationUtil.deserialize(fileName);
			System.out.println(newData);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
