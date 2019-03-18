package com.didispace.service;

import com.didispace.domain.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 对于不同数据源的事务管理配置,在声明事务时，
 * 只需要通过value属性指定配置的事务管理器名即可.
 * 例如:@Transactional(value="transactionManagerPrimary")
 */
public interface UserService {

    // 真正在开发业务逻辑时，我们通常在service层接口中使用@Transactional来对各个业务逻辑进行事务管理的配置
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    User login(String name, String password);

}
