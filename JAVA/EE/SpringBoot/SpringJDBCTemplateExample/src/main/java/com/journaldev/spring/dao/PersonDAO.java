package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.model.Person;

// Spring JDBC DAO类
// 最后一步是创建DAO类，使用sql查询将我们的模型类映射到数据库表。 
// 我们还将使用@Autowired注释配置DataSource并expost(公开,暴露)一些API。
public interface PersonDAO {
	Person getPersonById(Long id);

	List<Person> getAllPersons();

	boolean deletePerson(Person person);

	boolean updatePerson(Person person);

	boolean createPerson(Person person);
}
