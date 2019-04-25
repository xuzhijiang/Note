package hu.daniel.hari.learn.spring.orm.model;

import javax.persistence.Entity;
import javax.persistence.Id;

// Model

// 我们可以使用标准的JPA annotations在我们的Model beans中进行映射，因为Hibernate提供了JPA实现。

// 我们使用@Entity和@Id JPA注释来将我们的POJO限定为Entity(实体)并定义它的primary key(主键)。

@Entity
public class Product {

	@Id
	private Integer id;
	private String name;

	public Product() {
	}

	public Product(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}

}
