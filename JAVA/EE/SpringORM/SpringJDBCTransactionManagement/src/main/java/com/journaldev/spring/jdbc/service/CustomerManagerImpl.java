package com.journaldev.spring.jdbc.service;

import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.jdbc.dao.CustomerDAO;
import com.journaldev.spring.jdbc.model.Customer;

// 如果您注意到CustomerManager实现，它只是使用CustomerDAO实现来创建customer，
// 但通过使用@Transactional注释来注解createCustomer（）方法来提供声明式事务管理。 
// 这就是我们在代码中需要做的所有事情，以获得Spring事务管理的好处。

//@Transactional注释可以应用于methods和整个class。
// 如果您希望所有方法都具有事务管理功能，则应使用此批注对类进行批注。 

// 剩下的唯一部分是wiring spring beans ，以使spring 事务管理示例工作。
public class CustomerManagerImpl implements CustomerManager {

	private CustomerDAO customerDAO;

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	@Transactional
	public void createCustomer(Customer cust) {
		customerDAO.create(cust);
	}

}
