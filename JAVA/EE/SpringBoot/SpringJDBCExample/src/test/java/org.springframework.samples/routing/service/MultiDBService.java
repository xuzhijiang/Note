package org.springframework.samples.routing.service;

import com.legacy.springmvc.jdbc.routing.Routing;
import com.legacy.springmvc.jdbc.routing.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.routing.entity.User;
import org.springframework.samples.routing.entity.UserAccount;
import org.springframework.samples.routing.mapper.db1.UserInsertMapper;
import org.springframework.samples.routing.mapper.db1.UserSelectMapper;
import org.springframework.samples.routing.mapper.db2.UserAccountMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

public class MultiDBService {

    @Autowired
    UserSelectMapper userSelectMapper;

    @Autowired
    UserInsertMapper userInsertMapper;

    @Autowired
    UserAccountMapper userAccountMapper;

    // 业务层(service层)方法调用:

    // Mapper映射器接口属于dao层，通常dao层的代码都是在service层进行调用的，
    // 业务层的接口也可以添加@Routing注解，如果没有添加。则由调用的Mapper映射器方法、
    // 接口上的@Routing注解决定使用哪个ds，如果都没有没有定义，则使用默认的数据源
    public void testNoRouting(int userId, int userAccountId){
        User user = userSelectMapper.selectById(userId);
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
    }

    @Routing("ds1")
    public void testRouting(int userId, int userAccountId){
        //user表位于db1中，因此访问可以成功
        User user = userSelectMapper.selectById(userId);
        //注意：user_account表位于db2中，这里强制使用ds1，因此将执行失败
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
    }

    // 只使用@Transactional注解，不使用@Routing注解
    // 这将使用我们配置的默认数据源，如果访问了其他库中的表，将会报错，也就是说，
    // 使用了@Transactional注解后，也会忽略内部调用的其他方法的@Routing注解
    @Transactional
    public void testOnlyTransactional(User user, UserAccount userAccount){
        //默认数据源是ds1，可以访问db1中user表，因此插入成功
        userInsertMapper.insert(user);
        //注意：这个方法将执行失败，事务将回滚，因为user_account位于db2中
        userAccountMapper.insert(userAccount);
    }

    // 业务层方法添加@Routing注解后，将忽略内部调用的Mapper映射器方法、接口上的Routing注解
    // 指定方法内部调用的映射器接口，都必须使用ds2

    // 之所以一个方法使用了@Routing注解后，将会忽略内部调用的其他方法的@Routing注解，
    // 主要是为了与事务的语义兼容
    @Transactional
    @Routing("ds2")// 同时使用@Transactional/@Routing注解,spring事务管理器将会使用@Routing注解中指定的数据源来开启事务 ,即使用ds2开启事务.
    public void testRoutingTransaction(UserAccount userAccount){
        assert "ds2".equals(RoutingContext.getCurrentDataSource());
        assert !StringUtils.isEmpty(TransactionSynchronizationManager.getCurrentTransactionName());

        userAccountMapper.insert(userAccount);

        //注意：这个方法将执行失败，事务将回滚，因为user位于db1中
        User user = userSelectMapper.selectById(1);// 抛出异常，找不到表.
    }

}

// 问：想问一下大神，Mapper上添加切换数据源注解的作用，我觉得没用，因为事务已经在service开启了，开启了事务说明已经选择了对应的数据源，而Mapper层是由service层调用，这时候再选择数据源没有用

// 答: 有用的。有可能业务层并没有开启事务，且在业务层的方法需要访问多个库。这个时候就需要由Mapper层的注解决定了。