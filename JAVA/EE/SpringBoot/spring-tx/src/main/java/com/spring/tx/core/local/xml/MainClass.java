package com.spring.tx.core.local.xml;

import com.spring.tx.core.local.xml.dao.BookShopDao;
import com.spring.tx.core.local.xml.service.BookShopService;
import com.spring.tx.core.local.xml.service.Cashier;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

// 本地事务主类
public class MainClass {

    //创建IOC容器对象
    ApplicationContext ioc = new ClassPathXmlApplicationContext("beans-tx.xml");

    /**
     * 这个方法是用于测试事务的传播属性的.
     */
    @Test
    public void testCashier() {
        Cashier cashier = (Cashier) ioc.getBean("cashier");
        //创建List
        List<String> isbns = new ArrayList<>();
        isbns.add("1001");
        isbns.add("1002");
        //去结账
        cashier.checkout(1, isbns);
    }

    // 测试隔离级别
    @Test
    public void testBookShopService() {
        BookShopService bookShopService = (BookShopService) ioc.getBean("bookShopService");
        bookShopService.purchase(1, "1001");
    }

}
