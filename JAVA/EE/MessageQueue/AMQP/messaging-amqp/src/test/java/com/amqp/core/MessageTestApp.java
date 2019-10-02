package com.amqp.core;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessagingApplication.class)
public class MessageTestApp {

    @Test
    public void producer() throws IOException, TimeoutException {
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

    @Test
    public void consumer() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 建立到代理服务器的连接
        Connection conn = factory.newConnection();
        // 获得信道
        final Channel channel = conn.createChannel();
        // 声明Exchange
        String exchangeName = "hello-exchange";
        channel.exchangeDeclare(exchangeName, "direct", true);
        // 声明队列
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("队列名称: " + queueName);
        String routingKey = "hola";
        // 绑定队列，通过键 hola 将队列和交换器绑定起来
        channel.queueBind(queueName, exchangeName, routingKey);

        while(true){
            // 消费消息
            boolean autoAck = false;
            String consumerTag = "";
            channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的路由键: " + routingKey);
                    System.out.println("消费的内容类型: " + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    // 确认消息
                    channel.basicAck(deliveryTag, false);
                    System.out.println("消费的消息体内容:");
                    String bodyStr = new String(body, "UTF-8");
                    System.out.println(bodyStr);
                }
            });
        }
    }
}
