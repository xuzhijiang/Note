package com.journaldev.hibernate.model;

import java.util.Set;

//  一个购物车(Cart)可以有多个商品(Items)
// 我们可以使用外键约束进行一对多映射
public class Cart {

	private long id;
	private double total;
	private String name;
	private Set<Items> items;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Items> getItems() {
		return items;
	}
	public void setItems(Set<Items> items) {
		this.items = items;
	}
	
}
