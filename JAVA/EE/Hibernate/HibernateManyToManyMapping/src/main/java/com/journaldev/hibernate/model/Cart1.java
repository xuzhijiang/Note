package com.journaldev.hibernate.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// 每个购物车可以有多个商品(item)
// 每个商品也可以是多个购物车(cart)的一部分
// 建立多对多的映射关系.查看Cart_Items表就明白了.
@Entity
@Table(name = "CART")
public class Cart1 {

	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "cart_total")
	private double total;

	// 最重要的部分是使用ManyToMany批注和JoinTable批注，
	// 我们提供用于多对多映射的表名和列。
	@ManyToMany(targetEntity = Item1.class, cascade = { CascadeType.ALL })
	@JoinTable(name = "CART_ITEMS", 
				joinColumns = { @JoinColumn(name = "cart_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "item_id") })
	private Set<Item1> items;

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

	public Set<Item1> getItems() {
		return items;
	}

	public void setItems(Set<Item1> items) {
		this.items = items;
	}

}
