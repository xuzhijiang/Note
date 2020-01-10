package com.spring.tx.core.local.xml.service.impl;

import java.util.List;

import com.spring.tx.core.local.xml.service.BookShopService;
import com.spring.tx.core.local.xml.service.Cashier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cashier")
public class CashierImpl implements Cashier {

    @Autowired
    private BookShopService bookShopService;

    @Transactional // 用spring的声明式事务注解, 给这个方法添加事务.
    @Override
    public void checkout(int userId, List<String> isbns) {
        for (String isbn : isbns) {
            // 调用BookShopService中purchase()方法,这个purchase方法也添加了事务@Transactional,
            // 问题来了,purchase到底是用自己的事务呢,还是使用checkout方法的事务呢.
            // 这个就是事务的传播行为.
            bookShopService.purchase(userId, isbn);
        }
    }

}
