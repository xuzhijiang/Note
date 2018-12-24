package com.journaldev.spring.model;

import com.journaldev.spring.aspect.Loggable;

/**
 * Model Class
 * a simple java bean
 */
public class Employee {

	private String name;
	
	public String getName() {
		System.out.println("------------ Employee getName() called");
		return name;
	}

	// Did you noticed that setName() method is annotated with 
	//Loggable annotation. It is a custom java annotation defined 
	// by us in the project. We will look into it’s usage later on.
	// 您是否注意到setName（）方法使用Loggable注释进行注释。 它是我们在项目中定
	//义的自定义Java注解。 我们稍后会研究它的用法。
	@Loggable
	public void setName(String nm) {
		System.out.println("------------ Employee setName() called");
		this.name=nm;
	}
	
	// 调用此方法会抛java.lang.RuntimeException: Dummy Exception
	public void throwException(){
		throw new RuntimeException("Dummy Exception");
	}
	
}
