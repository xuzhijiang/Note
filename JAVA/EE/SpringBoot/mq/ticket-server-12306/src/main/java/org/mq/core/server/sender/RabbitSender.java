package org.mq.core.server.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String exchange, String routeKey, String context) {
        try {
            amqpTemplate.convertAndSend(exchange, routeKey, context);
        } catch (Exception e) {
            // 补偿
            log.info("发送消息: " + context + " 到该交换机上: " + exchange + "出现异常失败");
        }
    }
}
