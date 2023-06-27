package org.mq.core.server.business;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.mq.core.server.sender.RabbitSender;
import org.mq.core.server.service.GrapService;
import org.mq.core.server.service.TicketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class TicketBusiness {
    public static String FAILED_MSG = "余票不足,请求失败,查询不到该票的任何信息";

    @Autowired
    GrapService grapService;

    @Autowired
    RabbitSender rabbitSender;

    @Autowired
    TicketService ticketService;

    // 统计成功数
    private AtomicInteger success = new AtomicInteger();
    // 统计失败数
    private AtomicInteger failed = new AtomicInteger();

    // 接受美团app发过来的mq里面的msg
    // str: OK ---> 购票提交
    @RabbitListener(queues = "sam.ticket")
    public void process(String str) {
        try {
            Long t1 = System.currentTimeMillis();
            log.info(Thread.currentThread().getName() + "=================> 正在处理美团app出票请求中....");

            // 调用内部抢票服务: grapService
            String ticketInfo = grapService.grapTicket();
            JSONObject ticketJo = JSONObject.parseObject(ticketInfo);

            String respCode = ticketJo.getString("respCode");

            // 2. 出票成功
            if ("2".equals(respCode)) {
                String ticketStr = ticketJo.getString("ticket");
                // 响应给美团
                rabbitSender.sendMessage("ticket-exchange.response",
                        "sam.ticket.routeKey.response", "出票成功: " + ticketStr);
                log.info(Thread.currentThread().getName() + "出票成功!出票信息为: " + ticketStr);
            } else if ("1".equals(respCode)) { // 1. 余票不足
                log.info(Thread.currentThread().getName() + "余票不足!请求失败,查询不到任何票信息!");
                rabbitSender.sendMessage("ticket-exchange.response",
                        "sam.ticket.routeKey.response", "=>出票失败: " + FAILED_MSG);
            } else {
                // 3. 抢票失败,余票不确定,---->重复抢 --------->抢票成功/没票
                while (true) {
                    // 抢的太快,cpu受不了,可以人为的sleep一段时间,这个是业务经验.
                    TimeUnit.MICROSECONDS.sleep(1000);
                    String ticketStr = grapService.grapTicket();
                    JSONObject ticketJoo = JSONObject.parseObject(ticketStr);
                    String respCode1 = ticketJoo.getString("respCode");
                    // 2. 出票成功
                    if ("2".equals(respCode1)) {
                        String ticketsStr = ticketJoo.getString("ticket");
                        // 响应给美团
                        rabbitSender.sendMessage("ticket-exchange.response",
                                "sam.ticket.routeKey.response", "====>再次抢票====>出票成功: " + ticketsStr);
                        log.info(Thread.currentThread().getName() + "====>再次抢票====>出票请求成功!出票信息为: " + ticketsStr);
                        break;
                    } else if ("1".equals(respCode1)) {
                        log.info(Thread.currentThread().getName() + "余票不足!再次出票====>请求失败,查询不到任何票信息!");
                        rabbitSender.sendMessage("ticket-exchange.response",
                                "sam.ticket.routeKey.response", "=====>再次请求====>出票失败: " + FAILED_MSG);
                        break;
                    } else {
                        log.info(Thread.currentThread().getName() + "===>再次抢票");
                        continue;
                    }
                }
            }

            log.info(Thread.currentThread().getName() + "================> 抢票耗时: ============>" + (System.currentTimeMillis() - t1) + "MS");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Thread.currentThread().getName() + "============> 请求失败, 系统正在维护中...........!");
        }
    }
}
