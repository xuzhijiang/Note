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

    public void sentMessage(String exchange, String routeKey, String content) {
        log.info("========发送消息到mq,等待支付宝消费  " + content);
        amqpTemplate.convertAndSend(exchange, routeKey, content);
    }
}
