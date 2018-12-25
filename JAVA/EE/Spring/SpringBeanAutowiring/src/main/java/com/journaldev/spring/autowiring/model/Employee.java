package com.journaldev.spring.autowiring.model;

// Spring @Autowired Annotation – Model Bean

public class Employee {

	// 我们将在spring bean配置文件spring.xml中初始化此属性值。
	private String name;

	public String getName() {
		System.out.println("Employee getName called name: " + name);
		return name;
	}

	public void setName(String name) {
		System.out.println("Employee setName called name: " + name);
		this.name = name;
	}
}
