package com.journaldev.hibernate.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CART")
public class Cart1 {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cart_id")
	private long id;
	
	@Column(name="total")
	private double total;
	
	@Column(name="name")
	private String name;

	// 重要一点是OneToMany注释，其中mappedBy变量用于定义将用于映射目的的Items1类中的属性。
	// 所以我们应该在Items1类中有一个名为“cart1”的属性。 不要忘记包含所有getter-setter方法。
	@OneToMany(mappedBy="cart1")
	private Set<Items1> items1;
	
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
	public Set<Items1> getItems1() {
		return items1;
	}
	public void setItems1(Set<Items1> items1) {
		this.items1 = items1;
	}
	
}
