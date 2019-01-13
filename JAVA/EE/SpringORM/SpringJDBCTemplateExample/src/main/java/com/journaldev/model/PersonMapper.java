package com.journaldev.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

// 要从数据库中获取数据，我们需要实现接口RowMapper。
// 此接口只有一个方法mapRow（ResultSet resultSet，int i），它将返回我们的模型类的一个实例（即Person）。
public class PersonMapper implements RowMapper<Person> {

	public Person mapRow(ResultSet resultSet, int i) throws SQLException {

		Person person = new Person();
		person.setId(resultSet.getLong("id"));
		person.setFirstName(resultSet.getString("first_name"));
		person.setLastName(resultSet.getString("last_name"));
		person.setAge(resultSet.getInt("age"));
		return person;
	}
}
