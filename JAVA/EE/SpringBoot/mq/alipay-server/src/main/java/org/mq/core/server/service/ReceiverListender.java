package org.mq.core.server.service;

import lombok.extern.slf4j.Slf4j;
import org.mq.core.server.service.AlipayService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReceiverListender {

    @Autowired
    AlipayService alipayService;

    @RabbitListener(queues = "sam.message.response")
    public void process(final String result) {
        log.info("==========>>>>>接受到余额宝转账成功的应答消息========"+result);
        alipayService.updateMessage(result);
    }
}
