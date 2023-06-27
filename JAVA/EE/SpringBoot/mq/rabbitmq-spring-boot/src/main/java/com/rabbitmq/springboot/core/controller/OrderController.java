package com.rabbitmq.springboot.core.controller;

import com.rabbitmq.springboot.core.domain.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/create/order")
    public Order createOrder(Long skuId, Integer num, Integer memberId) {
        // 创建订单
        String orderSn = UUID.randomUUID().toString().replaceAll("-", "");
        Order order = new Order(orderSn, skuId, num, memberId);

        // 订单创建成功之后,把这个订单发给队列,库存系统监听队列,拿到订单之后进行扣库存.
        // 所以只要上面订单创建成功,就把订单消息发送给队列
        rabbitTemplate.convertAndSend("order-exchange", "createOrder", order);

        rabbitTemplate.convertAndSend("user.order.delay.exchange","order_delay",order);
        return order;
    }
}
