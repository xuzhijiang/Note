package com.journaldev.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.journaldev.model.Person;

// 请注意，这是我们使用Hibernate相关类的唯一地方。 
// 这种模式使我们的实现变得灵活，易于从一种技术迁移到另一种技术。 
// 例如，如果我们想使用iBatis ORM框架，我们只需要为iBatis提供DAO实现，
// 然后更改spring bean配置文件。


// 在上面的例子中，我使用的是Hibernate session transaction management(会话事务管理),
// 但我们也可以使用@Transactional注释使用Spring声明式事务管理，
// 在Spring Transaction Management中阅读更多内容。
public class PersonDAOImpl implements PersonDAO {

	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
	@Override
	public void save(Person p) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(p);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> list() {
		Session session = this.sessionFactory.openSession();
		List<Person> personList = session.createQuery("from Person").list();
		session.close();
		return personList;
	}

}
