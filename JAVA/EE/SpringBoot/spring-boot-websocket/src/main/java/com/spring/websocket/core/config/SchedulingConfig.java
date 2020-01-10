package com.spring.websocket.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
// @EnableScheduling注解：启用spring boot的定时任务，这与“callback”方法相呼应，用于每隔1秒推送服务器端的时间
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/callback")
    Object callback(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", sdf.format(new Date()));
        return "callback";
    }

}
