package hu.daniel.hari.learn.spring.orm.main;

import hu.daniel.hari.learn.spring.orm.model.Product;
import hu.daniel.hari.learn.spring.orm.service.ProductService;

import java.util.Arrays;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

// 我们可以轻松地从main方法启动Spring容器。 我们得到了第一个依赖注入入口点，即服务类实例。 
// 初始化spring context后，ProductDao类引用会被注入到ProductService类。

// 在我们获得了ProducService实例之后，我们可以测试它的方法，
/// 由于Spring的代理机制，所有方法调用都将是事务性的。 我们还在此示例中测试回滚。

// 请注意，第二个事务将回滚，这就是产品列表未更改的原因。

public class SpringOrmMain {
	
	public static void main(String[] args) {
		
		//Create Spring application context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		
		//Get service from context. (service's dependency (ProductDAO) is autowired in ProductService)
		ProductService productService = ctx.getBean(ProductService.class);
		
		//Do some data operation
		
		productService.add(new Product(1, "Bulb"));
		productService.add(new Product(2, "Dijone mustard"));
		
		System.out.println("listAll: " + productService.listAll());
		
		//Test transaction rollback (duplicated key)
		
		try {
			productService.addAll(Arrays.asList(new Product(3, "Book"), new Product(4, "Soap"), new Product(1, "Computer")));
		} catch (DataAccessException dataAccessException) {
		}
		
		//Test element list after rollback
		System.out.println("listAll: " + productService.listAll());
		
		ctx.close();
		
	}
}
