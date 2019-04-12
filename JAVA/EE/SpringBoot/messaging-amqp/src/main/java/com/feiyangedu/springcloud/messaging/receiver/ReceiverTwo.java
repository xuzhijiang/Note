package com.feiyangedu.springcloud.messaging.receiver;

import com.feiyangedu.springcloud.messaging.MessagingApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = MessagingApplication.QUEUE_NAME)
public class ReceiverTwo {

    final Log log = LogFactory.getLog(getClass());

    @RabbitHandler
    public void process(String message){
        System.out.println("receiveTwo msg: " + message);
    }

}
