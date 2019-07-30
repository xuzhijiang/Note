package com.springboot.legacy.bean;

import com.springboot.legacy.annotation.Loggable;
import org.springframework.stereotype.Component;

@Component
public class Employee {

	private String name;
	
	public String getName() {
		return name;
	}

	@Loggable
	public void setName(String nm) {
		this.name=nm;
	}
	
	public void throwException(){
		throw new RuntimeException("Dummy Exception");
	}
	
}
