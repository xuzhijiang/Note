package com.legacy.springmvc.service.impl;

import java.util.List;

import com.legacy.springmvc.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.legacy.springmvc.dao.PersonDAO;
import com.legacy.springmvc.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonDAO personDAO;

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	public void addPerson(Person p) {
		this.personDAO.addPerson(p);
	}

	@Override
	public void updatePerson(Person p) {
		this.personDAO.updatePerson(p);
	}

	@Override
	public List<Person> listPersons() {
		return this.personDAO.listPersons();
	}

	@Override
	public Person getPersonById(int id) {
		return this.personDAO.getPersonById(id);
	}

	@Override
	public void removePerson(int id) {
		this.personDAO.removePerson(id);
	}

}
