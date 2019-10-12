package com.funtl.hello.spring.cloud.alibaba.rocketmq.provider.service;

import com.funtl.hello.spring.cloud.alibaba.rocketmq.provider.MySource;
import com.funtl.hello.spring.cloud.alibaba.rocketmq.provider.RocketMQProviderApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ProviderService  {

    @Autowired
    private MySource mySource;

    public void send(String msg) {
        mySource.o1().send(MessageBuilder.withPayload(msg).build());
    }

    public void sendOutput2(String msg) {
        mySource.o2().send(MessageBuilder.withPayload(msg).build());
    }

    public void sendOutput3(String msg) {
        mySource.o3().send(MessageBuilder.withPayload(msg).build());
    }
}
