package com.legacy.springmvc.dao;

import java.util.List;

import com.legacy.springmvc.model.Person;

public interface PersonDAO {
	void addPerson(Person p);
	void updatePerson(Person p);
	List<Person> listPersons();
	Person getPersonById(int id);
	void removePerson(int id);
}
