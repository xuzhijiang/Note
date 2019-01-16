package com.journaldev.main;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.dao.PersonDAO;
import com.journaldev.model.Person;

// 当我们执行上面的程序时，我们得到了很多与Hibernate相关的输出，
// 因为我没有正确设置日志，但这超出了本教程的范围。
public class SpringHibernateMain {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		
		PersonDAO personDAO = context.getBean(PersonDAO.class);
		
		Person person = new Person();
		person.setName("Pankaj"); person.setCountry("India");
		
		personDAO.save(person);
		
		System.out.println("Person::"+person);
		
		List<Person> list = personDAO.list();
		
		for(Person p : list){
			System.out.println("Person List::"+p);
		}
		
		context.close();
		
	}

}
