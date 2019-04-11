package com.tp.spring.amqp.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Develop Spring AMQP Publisher program
public class SpringAMQPRabbitSender {

    private static final String SENDER_XML = "springamqp-rabbit-sender-context.xml";

    // 第一步运行这个，发送消息到Queue
    // Run AMQP Publisher and observe messages in RabbitMQ Queue
    // 运行完成后，在 RabbitMQ控制台Queues中查看消息,
    // 可以看到我们的RabbitMQ队列已成功从AMQP Publisher收到10条消息
    public static void main(String[] args) throws Exception {
        AmqpTemplate amqpTemplate = (AmqpTemplate)(new ClassPathXmlApplicationContext(SENDER_XML)).getBean("amqpTemplate");
        int messagCount = 0;
        while (messagCount < 10){
            amqpTemplate.convertAndSend("tp.routingkey.1", "Message # " + messagCount++);
        }
        System.out.println( messagCount + " message(s) sent successfully.");
    }

}
