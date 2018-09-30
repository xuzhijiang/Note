package org.java.core.advanced.SystemClass;

import java.util.Properties;
import java.util.Set;

public class JavaSystemProperties {
	public static void main(String[] args) {
		//System class contains useful method to get the list of System properties, 
		//get specific property, set system property and clear any existing property
		
		Properties props = System.getProperties();
		Set<Object> keySet = props.keySet();
		for(Object obj : keySet) {
			String key = (String) obj;
			System.out.println("{"+obj+"="+System.getProperty(key)+"}");
		}
		
		//Get Specific Property
		System.out.println(System.getProperty("user.country"));

		//Clear property example
		System.clearProperty("user.country");
		System.out.println(System.getProperty("user.country")); //print null
		
		//Set System property
		System.setProperty("user.country", "IN");
		System.out.println(System.getProperty("user.country")); //prints "IN"
	}
}
