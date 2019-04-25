package hu.daniel.hari.learn.spring.orm.service;

import hu.daniel.hari.learn.spring.orm.dao.ProductDao;
import hu.daniel.hari.learn.spring.orm.model.Product;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// please note that:

// method之前没有@Transactional注释，但我们希望这些方法是事务性的。 
// 原因是我们使用AOP方法来配置哪些方法是事务性的，将在下面的spring.xml中配置。

// 我们使用Spring的@Autowired注释让ProductDao组件被依赖注入。
@Component
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public void add(Product product) {
		productDao.persist(product);
	}
	
	public void addAll(Collection<Product> products) {
		for (Product product : products) {
			productDao.persist(product);
		}
	}
	
	public List<Product> listAll() {
		return productDao.findAll();
		
	}

}
