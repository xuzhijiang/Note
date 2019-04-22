package org.springframework.samples.routing.service;

import com.journaldev.spring.jdbc.routing.Routing;
import com.journaldev.spring.jdbc.routing.RoutingContext;
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

    public void testNoRouting(int userId, int userAccountId){
        User user = userSelectMapper.selectById(userId);
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
    }

    @Routing("ds1")
    public void testRouting(int userId, int userAccountId){
        User user = userSelectMapper.selectById(userId);
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
    }

    @Transactional
    public void testOnlyTransactional(User user){
        userInsertMapper.insert(user);
        int i = 1 / 0;
    }

    @Transactional
    @Routing("ds2")
    public void testRoutingTransaction(UserAccount userAccount){
        assert "ds2".equals(RoutingContext.getCurrentDataSource());
        assert !StringUtils.isEmpty(TransactionSynchronizationManager.getCurrentTransactionName());

        userAccountMapper.insert(userAccount);

        User user = userSelectMapper.selectById(1);// 抛出异常，找不到表.
    }

}
