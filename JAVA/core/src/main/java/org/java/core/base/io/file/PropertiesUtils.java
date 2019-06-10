package org.java.core.base.io.file;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * 在java中，属性文件可以是具有键值对的普通属性文件，也可以是XML文件。
 */
public class PropertiesUtils {

	/**
	 * 从类路径读取属性文件
	 */
	@Test
	public void readPropertyFileFromClasspath() throws IOException {
		String propertyFileName = "DB.properties";

		Properties prop = new Properties();
		InputStream is = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertyFileName);
		if (is != null) {
			prop.load(is);
			System.out.println(propertyFileName + " loaded from Classpath::db.host= " + prop.getProperty("db.host"));
			System.out.println(propertyFileName + " loaded from Classpath::db.user= " + prop.getProperty("db.user"));
			System.out.println(propertyFileName + " loaded from Classpath::db.pwd= " + prop.getProperty("db.pwd"));
		} else {
			System.out.println("请检查属性文件是否存在!!!!");
		}
	}
	
	/**
	 * 从给的属性文件中读取键值
	 */
	@Test
	public void readAllKeys() throws IOException {
		String propertyFileName = "DB.properties";
		String xmlFileName = "DB.xml";

		Properties prop = new Properties();
		FileReader reader = new FileReader(propertyFileName);
		prop.load(reader);
		
		Set<Object> keys = prop.keySet();
		for(Object obj : keys) {
			System.out.println("key=" + obj + ", value=" + prop.getProperty(obj.toString()));
		}

		// 从xml中加载，首先要清空prop中所有属性
		prop.clear();
		InputStream is = new FileInputStream(xmlFileName);
		prop.loadFromXML(is);
		keys = prop.keySet();
		for(Object obj : keys){
			System.out.println("key=" + obj + ", value=" + prop.getProperty(obj.toString()));
		}
		
		is.close();
		reader.close();
	}

	/**
	 * 把信息写入属性文件(xml格式和properties格式)
	 */
	@Test
	public void writePropertyFile() throws IOException {
		String propertyFileName = "DB.properties";
		String xmlFileName = "DB.xml";

		Properties prop = new Properties();
		prop.setProperty("db.host", "localhost");
		prop.setProperty("db.user", "user");
		prop.setProperty("db.pwd", "password");

		prop.store(new FileWriter(propertyFileName), "properties format");
		prop.storeToXML(new FileOutputStream(xmlFileName), "xml format");
	}

	/**
	 * 读取属性文件
	 */
	@Test
	public void readPropertyFile() throws IOException {
		String propertyFileName = "DB.properties";
		String xmlFileName = "DB.xml";

		Properties prop = new Properties();
		FileReader reader = new FileReader(propertyFileName);
		prop.load(reader);

		System.out.println(propertyFileName +"::db.host = "+prop.getProperty("db.host"));
		System.out.println(propertyFileName +"::db.user = "+prop.getProperty("db.user"));
		System.out.println(propertyFileName +"::db.pwd = "+prop.getProperty("db.pwd"));
		//loading xml file now, first clear existing properties
		prop.clear();

		InputStream is = new FileInputStream(xmlFileName);
		prop.loadFromXML(is);
		System.out.println(xmlFileName +"::db.host = "+prop.getProperty("db.host"));
		System.out.println(xmlFileName +"::db.user = "+prop.getProperty("db.user"));
		System.out.println(xmlFileName +"::db.pwd = "+prop.getProperty("db.pwd"));
		// 释放所有的资源
		is.close();
		reader.close();
	}

}
