package hu.daniel.hari.learn.spring.orm.dao;

import hu.daniel.hari.learn.spring.orm.model.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

// DAO

// @Component是Spring注释，告诉Spring容器我们可以通过Spring IoC(依赖注入）使用这个类。

// 我们使用JPA @PersistenceContext注释来表示对EntityManager的依赖注入。 
// Spring根据spring.xml配置注入合适的EntityManager实例。

@Component
public class ProductDao {

	@PersistenceContext
	private EntityManager em;

	public void persist(Product product) {
		em.persist(product);
	}

	public List<Product> findAll() {
		return em.createQuery("SELECT p FROM Product p").getResultList();
	}

}
