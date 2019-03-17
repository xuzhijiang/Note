package com.feiyangedu.springcloud.messaging.receiver;

import com.feiyangedu.springcloud.messaging.MessagingApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 创建消息消费者Receiver。通过@RabbitListener注解定义该类对notify队列的监听，
 * 并用@RabbitHandler注解来指定对消息的处理方法
 * 。所以，该消费者实现了对notify队列的消费
 */
@Component
@RabbitListener(queues = MessagingApplication.QUEUE_NAME)
public class Receiver {

    final Log log = LogFactory.getLog(getClass());

    @RabbitHandler
    public void process(String message){
        log.debug("receive message: " + message);
    }
}
