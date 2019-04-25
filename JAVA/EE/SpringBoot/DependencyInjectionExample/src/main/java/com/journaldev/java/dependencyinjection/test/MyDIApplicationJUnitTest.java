package com.journaldev.java.dependencyinjection.test;

import com.journaldev.java.dependencyinjection.consumer.Consumer;
import com.journaldev.java.dependencyinjection.consumer.MyDIApplication;
import com.journaldev.java.dependencyinjection.injector.MessageServiceInjector;
import com.journaldev.java.dependencyinjection.service.MessageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// 通过模拟注入器和服务类来轻松测试我们的应用程序类。
public class MyDIApplicationJUnitTest {

    private MessageServiceInjector injector;

    @Before
    public void setUp() {
        // 我使用匿名类来模拟注入器和服务类
        injector = new MessageServiceInjector() {
            public Consumer getConsumer() {
                // mock the message service
                return new MyDIApplication(new MessageService() {
                    public void sendMessage(String msg, String rec) {
                        System.out.println("Msg: " + msg + ", rec: " + rec + ", Mock Message Service implementation");
                    }
                });
            }
        };
    }

    @Test
    public void test() {
        Consumer consumer = injector.getConsumer();
        consumer.processMessages("Hi xzj", "xzj@abc.com");
    }

    @After
    public void tear() {
        injector = null;
    }

}