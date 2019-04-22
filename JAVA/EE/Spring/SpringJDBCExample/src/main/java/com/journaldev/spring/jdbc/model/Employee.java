package com.journaldev.spring.jdbc.model;

// 我们将使用DAO Pattern进行JDBC操作，
// 所以让我们创建一个将为Employee表建模(model)的java bean。

public class Employee {

	private int id;
	private String name;
	private String role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString(){
		return "{ID="+id+",Name="+name+",Role="+role+"}";
	}
}
