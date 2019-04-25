package com.journaldev.spring.jdbc.service;

import com.journaldev.spring.jdbc.model.Customer;

//让我们创建一个Customer Service，它将使用CustomerDAO实现，
// 并在单个方法中在customer and address表中插入记录时提供事务管理。
public interface CustomerManager {

	public void createCustomer(Customer cust);
}
