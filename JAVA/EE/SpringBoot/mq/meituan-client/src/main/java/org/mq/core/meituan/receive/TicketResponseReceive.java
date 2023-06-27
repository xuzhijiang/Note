package org.mq.core.meituan.receive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class TicketResponseReceive {

    private static String FAILED_MSG = "请求失败,查询不到该票的任何信息";

    // 统计成功数
    private AtomicInteger success = new AtomicInteger();

    // 统计失败数
    private AtomicInteger failed = new AtomicInteger();

    @RabbitListener(queues = "sam.ticket.response")
    public void processMsg(String ticketInfo) {
        if (ticketInfo.contains("出票成功")) {
            success.getAndIncrement();
            log.info("========================> 出票成功=======>" + ticketInfo);
            log.info("========================> 剩余票数: =======>" + (1000 - success.get()));
        } else if (ticketInfo.contains("出票失败")) {
            failed.getAndIncrement();
            log.info("========================> 出票失败=======>" + ticketInfo + ", " + FAILED_MSG);
        }
    }
}
