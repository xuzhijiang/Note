package com.funtl.hello.spring.cloud.alibaba.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 定义name为input11/intput22/input33的binding
 */
public interface MySink {

    @Input("input11")
    SubscribableChannel input1();

    @Input("input22")
    SubscribableChannel input2();

    @Input("input33")
    SubscribableChannel input3();
}
