package org.mq.core.server.timer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.mq.core.server.dao.MessageMapper;
import org.mq.core.server.domain.Message;
import org.mq.core.server.config.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ScheduledService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RabbitSender rabbitSender;

    // 每60s执行一次
    @Scheduled(cron = "0/60 0/1 * * * ?")
    public void scheduledProcess() {
        log.info("===>>>>>>>>使用cron {}函数开启定时扫描.......", sdf.format(new Date()));
        List<Message> unconfirm = messageMapper.queryMessageByState("unconfirm");
        // 如果查到有未确认的消息,就再发一遍到mq队列
        if (unconfirm!=null && unconfirm.size()>0) {
            log.info("=============>>>查询到未确认的消息有: " + JSONObject.toJSONString(unconfirm));
            for (Message message : unconfirm) {
                log.info("==========>>>>>>>>定时器往mq队列发送未确认的消息: " + JSONObject.toJSONString(message));
                rabbitSender.sendMessage("exchange.message", "sam.message.routeKey", message);
            }
        }
    }
}
