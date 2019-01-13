package com.journaldev.spring.jdbc.dao;

import com.journaldev.spring.jdbc.model.Customer;

//让我们为Customer bean实现DAO，为简单起见，我们将只有一种方法在customer and address表中插入记录。

// 请注意，CustomerDAO 实现不会负责处理事务管理。
// 这样我们就可以实现关注点的分离，因为有时我们会从第三方获得DAO实现，而我们无法控制这些第三方的类。
public interface CustomerDAO {

	public void create(Customer customer);
}