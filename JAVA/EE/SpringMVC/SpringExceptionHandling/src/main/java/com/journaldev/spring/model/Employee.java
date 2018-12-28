package com.journaldev.spring.model;

/*
	我已经将Employee bean定义为Model class，
	但是我们的应用程序中使用它只是为了在特定场景中返回有效响应。 
	在大多数情况下，我们会故意抛出不同类型的例外。
*/
public class Employee {

	private String name;
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
