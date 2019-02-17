package com.journaldev.hibernate.model;

import java.util.Set;

// 通常使用连接表在数据库中实现多对多映射。 例如，
// 我们可以使用Cart和Item表以及Cart_Items表进行多对多映射。
public class Cart {

	private long id;
	private double total;

	private Set<Item> items;
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

}
