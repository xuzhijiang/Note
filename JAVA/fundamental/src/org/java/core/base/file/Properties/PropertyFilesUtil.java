package org.java.core.base.file.Properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Java属性文件用于存储键值对配置。 java.util.Properties类用于处理java中的属性文件。
 * 
 * 在java中，属性文件可以是具有键值对的普通属性文件，也可以是XML文件。
 * 
 * 在这个java属性文件示例中，我们将向您展示如何以两种格式编写属性文件，
 * 然后从两个配置文件中读取属性。
 * 
 * 我们还将向您展示如何从类路径加载属性文件以及如何从属性文件中读取所有键。
 * 
 * 注意属性文件中的注释，它是生成的，因为我们在编写文件时也传递了注释。
 * 如果我们将注释作为null传递，则属性文件中将不会有注释。
 * 
 * 当我们只提供文件名时，它会在项目根目录中查找文件，该文件存储属性文件的位置。
 * 但是当我们尝试从类路径加载属性文件时，它会抛出NullPointerException，
 * 因为它尝试从类路径加载文件，该类路径是项目的src目录。
 * 
 * 当我们使用相同的Properties对象加载其他属性文件时，
 * 我们应该使用clear（）方法清除它的内容。如果我们传递属性对象中没有存储值的任何键，
 * 则返回null。
 */
public class PropertyFilesUtil {
	
	public static void main(String[] args) {
		
		try {
			String propertyFileName = "DB.properties";
			String xmlFileName = "DB.xml";
			
			writePropertyFile(propertyFileName, xmlFileName);
			readPropertyFile(propertyFileName, xmlFileName);
			
			readAllKeys(propertyFileName, xmlFileName);
			readPropertyFileFromClasspath(propertyFileName);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * read property file from classpath
	 * @param propertyFileName
	 * @throws IOException
	 */
	private static void readPropertyFileFromClasspath(String propertyFileName) throws IOException {
		Properties prop = new Properties();
		
		// 该类路径是项目的src目录。
		prop.load(PropertyFilesUtil.class.getClassLoader().getResourceAsStream(propertyFileName));
		System.out.println(propertyFileName + " loaded from Classpath::db.host= " + prop.getProperty("db.host"));
		System.out.println(propertyFileName + " loaded from Classpath::db.user= " + prop.getProperty("db.user"));
		System.out.println(propertyFileName + " loaded from Classpath::db.pwd= " + prop.getProperty("db.pwd"));
		System.out.println(propertyFileName + " loaded from Classpath::XYZ= " + prop.getProperty("XYZ"));
		
	}
	
	/**
	 * read all the keys from the given property files
	 * @param propertyFileName
	 * @param xmlFileName
	 * @throws IOException 
	 */
	private static void readAllKeys(String propertyFileName, String xmlFileName) throws IOException {
		System.out.println("Start of readAllKeys");
		
		Properties prop = new Properties();
		FileReader reader = new FileReader(propertyFileName);
		
		prop.load(reader);
		
		Set<Object> keys = prop.keySet();
		for(Object obj : keys) {
			System.out.println(propertyFileName + "::key="+obj.toString()+"::value=" + prop.getProperty(obj.toString()));
		}
		//loading xml file now, first clear existing properties
		prop.clear();
		
		InputStream is = new FileInputStream(xmlFileName);
		prop.loadFromXML(is);
		keys = prop.keySet();
		for(Object obj : keys){
			System.out.println(xmlFileName + ":: Key="+obj.toString()+"::value="+prop.getProperty(obj.toString()));
		}
		
		//Now free all the resources
		is.close();
		reader.close();
		System.out.println("End of readAllKeys");
	}

	/**
	 * 
	 * @param propertyFileName
	 * @param xmlFileName
	 * @throws IOException 
	 */
	private static void readPropertyFile(String propertyFileName, String xmlFileName) throws IOException {
		System.out.println("Start of readPropertyFile");
		Properties prop = new Properties();
		FileReader reader = new FileReader(propertyFileName);
		prop.load(reader);
		System.out.println(propertyFileName +"::db.host = "+prop.getProperty("db.host"));
		System.out.println(propertyFileName +"::db.user = "+prop.getProperty("db.user"));
		System.out.println(propertyFileName +"::db.pwd = "+prop.getProperty("db.pwd"));
		System.out.println(propertyFileName +"::XYZ = "+prop.getProperty("XYZ"));
		//loading xml file now, first clear existing properties
		prop.clear();
		InputStream is = new FileInputStream(xmlFileName);
		prop.loadFromXML(is);
		System.out.println(xmlFileName +"::db.host = "+prop.getProperty("db.host"));
		System.out.println(xmlFileName +"::db.user = "+prop.getProperty("db.user"));
		System.out.println(xmlFileName +"::db.pwd = "+prop.getProperty("db.pwd"));
		System.out.println(xmlFileName +"::XYZ = "+prop.getProperty("XYZ"));
		//Now free all the resources
		is.close();
		reader.close();
		System.out.println("End of readPropertyFile");
	}
	
	private static void writePropertyFile(String propertyFileName, String xmlFileName) throws IOException {
		System.out.println("Start of writePropertyFile");
		
		Properties prop = new Properties();
		prop.setProperty("db.host", "localhost");
		prop.setProperty("db.user", "user");
		prop.setProperty("db.pwd", "password");
		prop.store(new FileWriter(propertyFileName), "DB Config File");
		
		System.out.println(propertyFileName + " written successfully");
		
		prop.storeToXML(new FileOutputStream(xmlFileName), "DB Config XML file");
		
		System.out.println(xmlFileName + " written successfully");
		System.out.println("End of writePropertyFile");
	}
}
