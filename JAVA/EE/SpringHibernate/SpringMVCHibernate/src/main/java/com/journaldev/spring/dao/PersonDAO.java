package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.model.Person;

// Hibernate DAO实现
// 我们将创建接口来声明我们将在项目中使用的方法。 
// 接下来，我们将为它提供hibernate特定的实现。
public interface PersonDAO {

	public void addPerson(Person p);
	public void updatePerson(Person p);
	public List<Person> listPersons();
	public Person getPersonById(int id);
	public void removePerson(int id);
}
