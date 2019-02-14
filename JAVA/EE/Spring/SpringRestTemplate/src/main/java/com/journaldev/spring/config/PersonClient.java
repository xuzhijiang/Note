package com.journaldev.spring.config;

import java.util.List;
import org.springframework.http.HttpStatus;
import com.journaldev.spring.model.Person;

// Spring RestTemplate Client class

// 最后一步是创建将使用上面定义的RestTemplate bean的客户端类。
public interface PersonClient {
	List<Person> getAllPerson();

	Person getById(Long id);

	HttpStatus addPerson(Person person);

	void updatePerson(Person person);

	void deletePerson(Long id);
}
