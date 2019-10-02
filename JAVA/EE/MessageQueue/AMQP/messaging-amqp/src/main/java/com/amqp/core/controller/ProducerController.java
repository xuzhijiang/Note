package com.amqp.core.controller;

import com.amqp.core.common.MessageQueueConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class ProducerController {

    final Log log = LogFactory.getLog(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage")
    public String sendMessage() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                LocalDateTime time = LocalDateTime.now();
                rabbitTemplate.convertAndSend(MessageQueueConstants.EXCHANGE, MessageQueueConstants.ROUTING_KEY, time.toString());
            }
        }).start();
        return "ok";
    }

    @Autowired
    AmqpTemplate amqpTemplate;

    @GetMapping(value = "/send")
    String sendMessage(@RequestParam(name = "message", defaultValue = "") String message) {
        if (message.isEmpty()) {
            message = "Message<" + UUID.randomUUID().toString() + ">";
        }

        amqpTemplate.convertAndSend(MessageQueueConstants.EXCHANGE, MessageQueueConstants.ROUTING_KEY, message);
        return "Message sent ok!";
    }

}
