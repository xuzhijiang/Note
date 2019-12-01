package com.amqp.core.service;

import com.amqp.core.domain.Payload;

public interface ProducerService {
    void sendToDirectExchange(Payload payload, String routingKey);

    void sendToTopicExchange(Payload payload, String routingKey);

    void sendToFanoutExchange(Payload payload);
}
