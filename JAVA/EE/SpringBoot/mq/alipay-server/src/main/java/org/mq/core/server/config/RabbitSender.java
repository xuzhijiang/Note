package org.mq.core.server.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.mq.core.server.domain.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String exchange, String routeKey, Message content) {
        try {
            String message = JSON.toJSONString(content);
            log.info("==========>>>>>>发送消息到mq,等待余额宝消费中:  " + message);
            amqpTemplate.convertAndSend(exchange, routeKey, message);
        } catch (Exception e) {

        }
    }
}
