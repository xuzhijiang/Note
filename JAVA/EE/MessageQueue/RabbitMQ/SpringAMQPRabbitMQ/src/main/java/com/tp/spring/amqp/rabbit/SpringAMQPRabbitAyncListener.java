package com.tp.spring.amqp.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
// Develop Spring AMQP Consumer(Spring MDP) program
// Spirng MDP(Message Driven POJO)
public class SpringAMQPRabbitAyncListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("Listener received message = " + new String(message.getBody()));
    }
}