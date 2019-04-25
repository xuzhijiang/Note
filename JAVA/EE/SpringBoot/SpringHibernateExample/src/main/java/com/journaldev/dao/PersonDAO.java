package com.journaldev.dao;

import java.util.List;

import com.journaldev.model.Person;

// 我们将在DAO类中实现两个方法，
// 首先将Person对象保存到表中，然后再从表中获取所有记录并返回Persons列表。
public interface PersonDAO {

	public void save(Person p);
	
	public List<Person> list();
	
}
