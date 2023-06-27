package com.rabbitmq.springboot.core;

import com.rabbitmq.springboot.core.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTestApplication {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void rabbitmqConnectionTest() {
        User user = new User("xiaoming", "aaaa@gmail.com");

        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        // 给指定的交换机(direct_exchange), 按照指定路由键(world), 发送消息(user)
        rabbitTemplate.convertAndSend("direct_exchange", "world", user);
        System.out.println("消息发送完成....");
    }

    @Test
    public void createQueue() {
        // Queue(String name, boolean durable, boolean exclusive, boolean autoDelete)
        // @param exclusive true if we are declaring an exclusive queue (the queue will only be used by the declarer's connection)
        // 是否排他, 如果设置为true,表示我们正在声明一个独占的队列,意思就是: 这个队列仅仅能够被一个声明的连接所使用,不能被其他连接使用,一般没人调这个
        // @param autoDelete true if the server should delete the queue when it is no longer in use
        // 如果服务器不再使用队列时,应该删除这个队列, (当这个不再使用这个queue的时候,会将它删除),
        // 也就是最后一个consumer都断开和queue的连接的时候,这个queue就会被删除
        Queue queue = new Queue("my-queue-01", true, false, false);
        amqpAdmin.declareQueue(queue);
        System.out.println("队列创建完成...");
    }

    @Test
    public void createExchange() {
        //DirectExchange(String name, boolean durable, boolean autoDelete)
        // @param autoDelete: 当不再使用的时候,会被自动删除
        Exchange exchange = new DirectExchange("my-exchange", true, false);
        amqpAdmin.declareExchange(exchange);
        System.out.println("DirectExchange创建完成...");
    }

    @Test
    public void createBinding() {
        //Binding(String destination, 目的地是哪里(哪个queue)
        //        DestinationType destinationType, 目的地类型 (目的地可以是queue,可以是exchange,也就是交换机可以和队列绑定,也可以和其他交换机绑定)
        //       String exchange, 交换机 (把destination绑定到哪个交换机)
        //       String routingKey, 路由键
        //	     Map<String, Object> arguments 参数)
        Binding binding = new Binding("my-queue-01", Binding.DestinationType.QUEUE,
                "my-exchange",
                "hello",
                null);
        amqpAdmin.declareBinding(binding);
        System.out.println("Binding创建完成...");
    }
}
