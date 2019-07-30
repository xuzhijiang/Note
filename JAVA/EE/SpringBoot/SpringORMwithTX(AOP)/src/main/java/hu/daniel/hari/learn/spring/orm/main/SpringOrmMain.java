package hu.daniel.hari.learn.spring.orm.main;

import hu.daniel.hari.learn.spring.orm.model.Product;
import hu.daniel.hari.learn.spring.orm.service.ProductService;

import java.util.Arrays;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

/// 由于Spring的代理机制，所有方法调用都将是事务性的。 我们还在此示例中测试回滚。

//请注意，第二个事务将回滚，这就是产品列表未更改的原因。

// 您可以看到我们制作了事务方法，而没有使用@Transactional注释逐个指定它们，
// 因为我们在AOP方法中对其进行了配置。

public class SpringOrmMain {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		
		//Get service from context. (service's dependency (ProductDAO) is autowired in ProductService)
		ProductService productService = ctx.getBean(ProductService.class);
		
		//Do some data operation
		
		productService.add(new Product(1, "Television"));
		productService.add(new Product(2, "Phone"));
		
		System.out.println("listAll: " + productService.listAll());
		
		//Test transaction rollback (for duplicated key)
		try {
			productService.addAll(Arrays.asList(
					new Product(3, "Peach"), new Product(4, "Strawberry"), new Product(1, "Melone")));
		} catch (DataAccessException dataAccessException) {
			//Do nothing here, we just test rollback
		}
		
		//Test element list after rollback (same two element, 3 more hasn't been added.)
		System.out.println("listAll: " + productService.listAll());
		
		ctx.close();
	}
}
