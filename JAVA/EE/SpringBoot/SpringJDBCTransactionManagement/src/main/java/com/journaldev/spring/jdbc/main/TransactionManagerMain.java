package com.journaldev.spring.jdbc.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.jdbc.model.Address;
import com.journaldev.spring.jdbc.model.Customer;
import com.journaldev.spring.jdbc.service.CustomerManager;
import com.journaldev.spring.jdbc.service.CustomerManagerImpl;

//请注意，我显式设置address column值太长，以便在将数据插入Address表时会出现异常。

//请注意，日志消息表明插入到customer表中的数据已成功
// 但MySQL数据库驱动程序抛出的异常清楚地表明该值对于address column(地址列)来说太长了。
// 现在，如果您将检查Customer表，那么您将找不到任何行，这意味着事务将完全回滚。

// 如果您想知道事务管理魔术在哪里发生，请仔细查看logs并注意Spring框架创建的AOP和Proxy类。 
// Spring框架使用Around advice为CustomerManagerImpl生成代理类，
// 并且只有在方法成功返回时才提交事务。 如果有任何异常，它只是回滚整个事务。
// 我建议你阅读Spring AOP示例，以了解有关 Aspect Oriented Programming model.的更多信息。

public class TransactionManagerMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring.xml");

		CustomerManager customerManager = ctx.getBean("customerManager",
				CustomerManagerImpl.class);

		Customer cust = createDummyCustomer();
		customerManager.createCustomer(cust);

		ctx.close();
	}

	private static Customer createDummyCustomer() {
		Customer customer = new Customer();
		customer.setId(2);
		customer.setName("Pankaj");
		Address address = new Address();
		address.setId(2);
		address.setCountry("India");
		// setting value more than 20 chars, so that SQLException occurs
		address.setAddress("Albany Dr, San Jose, CA 95129");
		customer.setAddress(address);
		return customer;
	}

}
