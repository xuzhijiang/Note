package org.mq.core.meituan.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sentMessage(String exchange, String routeKey, String context) {
        amqpTemplate.convertAndSend(exchange, routeKey, context);
    }
}
