package com.spring.websocket.core.controller;

import com.spring.websocket.core.domain.Msg;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebSocketController {

    @GetMapping("/")
    String index(){
        return "index";
    }

    // “send”方法用于接收客户端发送过来的websocket请求。
    @MessageMapping("/send")
    @SendTo("/topic/send")
    Msg send(Msg msg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        msg.date = sdf.format(new Date());
        return msg;
    }

}
