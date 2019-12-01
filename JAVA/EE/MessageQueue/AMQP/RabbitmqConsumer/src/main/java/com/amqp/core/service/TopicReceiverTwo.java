package com.amqp.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 会公平调度.ConsumerService中的和这里的一人一次
 */
@Component
@RabbitListener(queues = "${queue.a}")
public class TopicReceiverTwo {

    final Log log = LogFactory.getLog(getClass());
    final AtomicInteger count = new AtomicInteger(0);

    // 用@RabbitHandler注解来指定对消息的处理方法
    @RabbitHandler
    public void process(String message){
        log.info("msg from two, msg: " + message + ", count: " + count.incrementAndGet());
    }

}
