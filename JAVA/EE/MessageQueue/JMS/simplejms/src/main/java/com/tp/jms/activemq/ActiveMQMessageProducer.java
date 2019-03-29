package com.tp.jms.activemq;

import javax.annotation.PostConstruct;
import javax.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
// Develop Spring AMQP Messaging Application With ActiveMQ

// a Spring AMQP ActiveMQ Messaging application
// using Queues that is One-to-One Messaging Application.

// Develop Spring ActiveMQ AMQP Publisher program
@Component
public class ActiveMQMessageProducer {

    protected static final String MSG_COUNT = "messageCount";

    @Autowired
    private JmsTemplate jmsTemplate = null;

    @PostConstruct
    public void generateMessages() throws JMSException
    {
        for (int messageCount = 0; messageCount < 10; messageCount++)
        {
            final String text = "TP Message " + messageCount;
            jmsTemplate.send(new MessageCreator()
            {
                public Message createMessage(Session session) throws JMSException
                {
                    TextMessage textMessage = session.createTextMessage(text);
                    textMessage.setIntProperty(MSG_COUNT, messageCount);
                    return textMessage;
                }
            });
        }
    }
}