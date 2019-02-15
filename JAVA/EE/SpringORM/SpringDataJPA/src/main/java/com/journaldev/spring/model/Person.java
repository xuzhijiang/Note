package com.journaldev.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity// @Entity：此批注允许实体管理器使用此类并将其放在上下文中。
@Table(name = "people")// @Table（name =“people”）：将一个类与数据库中的表相关联。
public class Person {

	@Id// @Id：说这个字段是主键。
	@GeneratedValue(strategy = GenerationType.IDENTITY)// @GeneratedValue（strategy = GenerationType.IDENTITY）：定义生成主键的策略。
	private Long id;

	// @Column（name =“age”）：表示数据库中与该字段关联的列。
	@Column(name = "age")
	private Integer age;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;

	public Person() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person{" + "id=" + id + ", age=" + age + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + '}';
	}
}
