package com.spring.tx.core.local;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("beans-tx.xml");

    // 测试事务注解的基本使用
    @Test
    public void testBookShopService() {
        BookService bookService = ioc.getBean(BookService.class);
        // id为1的用户购买了一本书,书号是1001,进行结账
        bookService.checkout(1, "1001");
        System.out.println("结账完成");
    }

    /**
     * 正确测试事务的传播行为
     */
    @Test
    public void testMultiServiceRight() {
        MultiService multiService = ioc.getBean(MultiService.class);
        multiService.multiTx();
    }

    /**
     * 错误的测试事务的传播行为的方式
     */
    @Test
    public void testMultiServiceWrong() {
        BookService bookService = ioc.getBean(BookService.class);
        bookService.multiTx();
    }
}
