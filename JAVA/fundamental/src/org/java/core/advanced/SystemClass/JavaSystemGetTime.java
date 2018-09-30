package org.java.core.advanced.SystemClass;

import java.util.Date;

public class JavaSystemGetTime {
	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis);
		
		System.out.println("Current time in millis="+currentTimeMillis);
		System.out.println(date);
		
		long nanoTime = System.nanoTime();//дицК
		System.out.println("Current nano time: "+nanoTime);
	}
}
