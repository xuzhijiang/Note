package com.amqp.core.service.impl;

import com.amqp.core.domain.Payload;
import com.amqp.core.service.ProducerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Value("${exchange.direct}")
    private String directExchange;

    @Value("${exchange.topic}")
    private String topicExchange;

    @Value("${exchange.fanout}")
    private String fanoutExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public void sendToDirectExchange(Payload payload, String routingKey) {
        rabbitTemplate.convertAndSend(directExchange, routingKey, payload);
    }

    @Override
    public void sendToTopicExchange(Payload payload, String routingKey) {
        amqpTemplate.convertAndSend(topicExchange, routingKey, payload);
    }

    @Override
    public void sendToFanoutExchange(Payload payload) {
        // routingKey is ignored
        rabbitTemplate.convertAndSend(fanoutExchange, "", payload);
    }
}
