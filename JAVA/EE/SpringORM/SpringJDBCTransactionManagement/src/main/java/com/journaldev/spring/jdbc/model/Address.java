package com.journaldev.spring.jdbc.model;

// 我们将创建两个Java Bean，Customer和Address，它们将映射到我们的表。

// 请注意，Customer bean将Address作为其中一个变量。
// 当我们为Customer implement DAO时，我们将获得customer and address 表的数据，
// 我们将为这些表执行两个单独的插入查询，这就是我们需要事务管理以避免数据不一致的原因。
public class Address {

	private int id;
	private String address;
	private String country;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
