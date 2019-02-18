package com.tp.jms.activemq;

import static org.junit.Assert.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// Develop a Test application

// 由于此单元测试名称为ActiveMQJmsMessageListenerTest，
// 因此@ContextConfiguration批注在相同的包结构中搜索
// ActiveMQJmsMessageListenerTest-context.xml文件。
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ActiveMQJmsMessageListenerTest {

    @Autowired
    private AtomicInteger count = null;

    @Test
    public void testMessage() throws Exception {
        assertEquals(10, count.get());
    }
}