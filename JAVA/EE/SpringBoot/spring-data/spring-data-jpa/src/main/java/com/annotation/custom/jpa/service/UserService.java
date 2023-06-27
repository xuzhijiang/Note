package com.annotation.custom.jpa.service;

import com.annotation.custom.jpa.dao.BaseDao;
import com.annotation.custom.jpa.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 对于不同数据源的事务管理,在声明事务时，只需要通过value属性指定配置的事务管理器名即可.
 * 例如:@Transactional(value="transactionManagerPrimary")
 */
@Service
public class UserService {

    @Autowired
    private BaseDao<User> baseDao;

    public void add(User user) {
        baseDao.add(user);
    }

    // 真正在开发业务逻辑时，我们通常在service层接口中使用@Transactional来对各个业务逻辑进行事务管理的配置
    // @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    // User login(String name, String password);
    // 
    // @Transactional("accountTxManager")  //指明要使用的事务管理器
    // 事务管理器有dataSource,不同的dataSource可能操作不同的数据库和表
}
