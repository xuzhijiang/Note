package com.tp.spring.amqp.rabbit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// Develop Spring AMQP Rabbit Container program to initialize Spring IOC Container
public class SpringAMQPRabbitlListenerContainer {
    // 第二步运行这里，消费Queue中的消息
    // Run AMQP Consumer and observe messages in IDE
    // 可以看到AMQP Consumer从RabbitMQ队列中逐个接收每条消息。
    // 此时:可以在RabbitMQ控制台观察到RabbitMQ队列有0条消息，这意味着AMQP Consumer已成功接收到所有消息。
    public static void main(String[] args) {
        // Initialize Spring IOC Container
        new ClassPathXmlApplicationContext("springamqp-rabbt-listener-context.xml");
    }
}