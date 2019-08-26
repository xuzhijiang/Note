package com.feiyangedu.springcloud.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // create connection factory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 设置RabbitMQ地址
        factory.setHost("localhost");
        // 建立到服务器的连接
        Connection conn = factory.newConnection();
        // 获得信道
        Channel channel = conn.createChannel();
        // 声明交换机
        String exchangeName = "hello-exchange";
        // parameter 1: 交换机名称
        // parameter 2: 交换机类型
        // parameter 3: 是否是durable
        channel.exchangeDeclare(exchangeName, "direct", true);

        String routingKey = "hola";
        // 发布消息
        byte[] messageBodyBytes = "quit".getBytes();
        channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

        channel.close();
        conn.close();
    }

}
