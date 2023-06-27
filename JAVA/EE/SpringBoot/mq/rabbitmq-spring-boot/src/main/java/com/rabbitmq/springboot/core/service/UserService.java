package com.rabbitmq.springboot.core.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.springboot.core.domain.Order;
import com.rabbitmq.springboot.core.domain.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    /**
     * 方法上可以写以下参数
     * 1、org.springframework.amqp.core.Message：既能获取到消息的内容字节，还能获取到消息的其他Message属性
     * 2、User user：如果明确我们这个队列以后都是这个类型对象，可以直接写这个类型的参数
     * 3、com.rabbitmq.client.Channel：信道；拿到通道可以拒绝消息,或者确认消息.
     *
     * 以上无任何顺序，也无数量限制
     */
    @RabbitListener(queues = {"world"}) // 从哪个队列中收消息
    public void receiveUserMessage(Message message, User user, Channel channel) throws IOException {
        System.out.println("收到的消息的类型是: " + message.getClass());
        byte[] body = message.getBody();// 获取消息体
        // 消息中的其他属性
        MessageProperties messageProperties = message.getMessageProperties();// 获取消息属性

        System.out.println("收到的消息是: " + user);

        // 假设有3个扣库存的服务, 这个消息发给其中一个扣库存服务之后,但是这个服务扣库存失败了,
        // 此时就可以把消息拒绝掉, 让 rabbitmq 再发给别人进行扣库存.

        // 这里由于只有一个消息的listener,所以又发给了我自己,所以会出现不停的调用这个方法

        // 第一个参数: 派发消息用的标签   第二个参数: 是否要重新入队(requeue)
        // 如果写false,说明这个消息我不要了,你mq也要不要把它再入队重新发给别人了,直接丢弃即可.
        // 如果true,表示可以重新入队,再发给别人.这个要依据不同场景设置.
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
    }

}
