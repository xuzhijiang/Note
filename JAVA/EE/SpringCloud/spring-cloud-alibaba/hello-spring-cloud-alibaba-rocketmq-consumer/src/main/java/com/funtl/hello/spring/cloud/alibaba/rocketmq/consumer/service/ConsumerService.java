package com.funtl.hello.spring.cloud.alibaba.rocketmq.consumer.service;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    // 主要使用 @StreamListener("input11") 注解来订阅从名为 input11 的 Binding 中接收的消息,input11为binding的名字
    @StreamListener("input11")
    public void receiveInput11(String msg){
        System.out.println("Receive Input11: " + msg);
    }

    @StreamListener("input22")
    public void receiveInput22(String msg){
        System.out.println("Receive input22: " + msg);
    }

    @StreamListener("input33")
    public void receiveInput33(String msg)  {
        System.out.println("Receive Input33: " + msg);
    }
}
