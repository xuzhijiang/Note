package com.amqp.core.receiver;

import com.amqp.core.MessagingApplication;
import com.amqp.core.common.MessageQueueConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Receiver和ReceiverTwo会公平调度.
 */
@Component
// 对QUEUE_NAME队列的监听
@RabbitListener(queues = MessageQueueConstants.QUEUE_NAME)
public class ReceiverTwo {

    final AtomicInteger count = new AtomicInteger(0);

    // 用@RabbitHandler注解来指定对消息的处理方法
    @RabbitHandler
    public void process(String message){
        int n = count.incrementAndGet();
        System.out.println("Receiver Two msg count: " + n);
    }

}
