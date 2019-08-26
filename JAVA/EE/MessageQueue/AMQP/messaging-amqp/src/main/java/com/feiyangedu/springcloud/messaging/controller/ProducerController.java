package com.feiyangedu.springcloud.messaging.controller;

import com.feiyangedu.springcloud.messaging.MessagingApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage")
    public String sendMessage() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                LocalDateTime time = LocalDateTime.now();
                System.out.println("send message: " + time.toString());
                // 直接用 Spring 的 RabbitTemplate 模版，根据交换器和路由键，将消息路由到特定队列。
                rabbitTemplate.convertAndSend(MessagingApplication.EXCHANGE, MessagingApplication.ROUTING_KEY, time.toString());
            }
        }).start();
        return "ok";
    }
}
