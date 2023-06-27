package com.rabbitmq.springboot.core.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.springboot.core.domain.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderService {

    // 监听订单队列,拿到订单队列中的 订单消息 之后,就扣库存
    @RabbitListener(queues = {"order-queue"})
    public void receiveOrder(Order order, Message message, Channel channel) throws IOException {
        System.out.println("监听到新的订单生成...."+order);

        Long skuId = order.getSkuId();
        Integer num = order.getNum();
        System.out.println("库存系统正在扣除【"+skuId+"】商品的数量，此次扣除【"+num+"】件");

        if (num % 2 == 0) {
            System.out.println("库存系统扣除【"+skuId+"】库存失败");
            // 回复给mq处理失败，也就是拒绝处理这条消息。让消息重新入队
            // 第二个参数: mutiple,如果为true,表示回复这个信道的全部消息,但是我们肯定只回复我们当前这条消息,所以设置为false(表示只回复本条消息)
            // 第三个参数: requeue: 是否重新入队,如果这里设置为false,意思就是这个消息我这里处理失败了,但是告诉mq丢弃这个消息,也不让别人处理
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // 可以拒绝这个channel中的全部消息
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 只拒绝这个channel的本条消息
            throw new RuntimeException("库存扣除失败");
        }

        System.out.println("扣除成功....");
        // 只回复本条消息,basicAck()表示正确接收到消息
        // 第一个参数: 这个消息的派发标记
        // 第二个参数: mutiple,如果为true,表示回复这个信道的全部消息,但是我们肯定只回复我们当前这条消息,所以设置为false(表示只回复本条消息)
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = {"user.order.queue"})
    public void closeOrder(Order order, Channel channel, Message message) throws IOException {
        // 需要判断下,订单是否支付,如果没有支付,就关闭,如果支付了,就不管.
        System.out.println("收到过期订单："+order+"正在关闭订单");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
