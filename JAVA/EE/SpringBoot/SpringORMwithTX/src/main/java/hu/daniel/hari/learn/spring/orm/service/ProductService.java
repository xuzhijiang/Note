package hu.daniel.hari.learn.spring.orm.service;

import hu.daniel.hari.learn.spring.orm.dao.ProductDao;
import hu.daniel.hari.learn.spring.orm.model.Product;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
//Service

// 我们使用Spring @Autowired注释将我们的服务类中注入到ProductDao。

// 我们想要使用事务管理，因此methods使用@Transactional Spring注释进行注释。 
// listAll方法只读取数据库，因此我们将@Transactional注释设置为只读以进行优化。

@Component
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Transactional
	public void add(Product product) {
		productDao.persist(product);
	}
	
	@Transactional
	public void addAll(Collection<Product> products) {
		for (Product product : products) {
			productDao.persist(product);
		}
	}

	@Transactional(readOnly = true)
	public List<Product> listAll() {
		return productDao.findAll();

	}

}
