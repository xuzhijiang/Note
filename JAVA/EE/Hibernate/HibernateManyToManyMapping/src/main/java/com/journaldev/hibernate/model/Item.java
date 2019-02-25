package com.journaldev.hibernate.model;

import java.util.Set;

// 请注意，Cart有一组Item和Item有一组Cart，这样我们就可以实现双向关联。
// 这意味着我们可以将它配置为"在保存Cart时" 保存Item，反之亦然。

//对于单向映射，通常我们在其中一个模型类中进行设置。
// 我们将使用基于注释用于进行单向映射。
public class Item {

	private long id;
	private double price;
	private String description;

	private Set<Cart> carts;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Cart> getCarts() {
		return carts;
	}

	public void setCarts(Set<Cart> carts) {
		this.carts = carts;
	}
}
