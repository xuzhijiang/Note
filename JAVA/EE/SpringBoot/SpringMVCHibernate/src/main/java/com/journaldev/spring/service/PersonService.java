package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.model.Person;
// Spring Service Class
// 以下是我们的服务类，它们使用Hibernate DAO类来处理Person对象。
public interface PersonService {

	public void addPerson(Person p);
	public void updatePerson(Person p);
	public List<Person> listPersons();
	public Person getPersonById(int id);
	public void removePerson(int id);
	
}
