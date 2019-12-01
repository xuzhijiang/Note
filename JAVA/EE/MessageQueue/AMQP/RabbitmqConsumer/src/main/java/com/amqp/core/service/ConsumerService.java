package com.amqp.core.service;

import com.amqp.core.domain.Payload;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class ConsumerService {

    final AtomicLong counter = new AtomicLong(0L);

    @RabbitListener(queues = "${queue.a}")
    public void handleQueueAMessageReception(Payload payload) {
        System.out.println("queueA msg: " + payload.getMessage() + ", count: " + counter.incrementAndGet() + ", queue a");
    }

    @RabbitListener(queues = "${queue.b}")
    public void handleQueueBMessageReception(Payload payload) {
        System.out.println(payload.getMessage() + ", queue b");
    }

    @RabbitListener(queues = "${queue.c}")
    public void handleQueueCMessageReception(Payload payload) {
        System.out.println(payload.getMessage() + ", queue c");
    }

    @RabbitListener(queues = "${queue.d}")
    public void handleQueueDMessageReception(Payload payload) {
        System.out.println(payload.getMessage() + ", queue d");
    }

    @RabbitListener(queues = "${queue.e}")
    public void handleQueueEMessageReception(Payload payload) {
        System.out.println(payload.getMessage() + ", queue e");
    }

    @RabbitListener(queues = "${queue.f}")
    public void handleQueueFMessageReception(Payload payload) {
        System.out.println(payload.getMessage() + ", queue f");
    }
}
