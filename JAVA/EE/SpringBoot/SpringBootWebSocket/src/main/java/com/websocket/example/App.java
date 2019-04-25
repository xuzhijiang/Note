package com.websocket.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@EnableScheduling
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @GetMapping("/")
    String index(){
        return "index";
    }

    /**
     * “send”方法用于接收客户端发送过来的websocket请求。
     * @param msg
     * @return
     */
    @MessageMapping("/send")
    @SendTo("/topic/send")
    SocketMsg send(SocketMsg msg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        msg.date = sdf.format(new Date());
        return msg;
    }

    /**
     * @EnableScheduling注解为：启用spring boot的定时任务，这与“callback”方法相呼应，用于每隔1秒推送服务器端的时间。
     * @return
     */
    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/callback")
    Object callback(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", sdf.format(new Date()));
        return "callback";
    }
}
