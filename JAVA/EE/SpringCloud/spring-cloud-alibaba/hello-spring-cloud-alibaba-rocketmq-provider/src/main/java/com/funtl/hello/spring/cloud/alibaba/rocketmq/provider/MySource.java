package com.funtl.hello.spring.cloud.alibaba.rocketmq.provider;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 在实际生产中，我们需要发布和订阅的消息可能不止一种 Topic ，故此时就需要使用自定义 Binding 来帮我们
 * 实现多 Topic 的发布和订阅功能
 */
public interface MySource {
    /**
     * 分别使用name为output1/output2/output3的binding发送
     * @return
     */
    @Output("output1")
    MessageChannel o1();

    @Output("output2")
    MessageChannel o2();

    @Output("output3")
    MessageChannel o3();
}