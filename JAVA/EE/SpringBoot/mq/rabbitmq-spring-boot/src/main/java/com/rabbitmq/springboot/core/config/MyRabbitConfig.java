package com.rabbitmq.springboot.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 直接用程序帮我们创建exchange,queue,binding,不用我们手动创建
// 如果RabbitMq没有这个队列、交换机、绑定关系会自动创建。有的话就不会再创建了,直接使用
@Configuration
public class MyRabbitConfig {

    /**
     * 默认采用的是jdk的序列化,我们要自动配置成json格式的序列化
     * 当然也可以转成xml: Jackson2XmlMessageConverter
     * @return 自定义RabbitMQ的MessageConverter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 订单队列
    @Bean
    public Queue orderQueue(){
        return new Queue("order-queue",true,false,false,null);
    }

    // 订单交换机
    @Bean
    public Exchange orderExchange(){
        return new DirectExchange("order-exchange",true,false,null);
    }

    // 订单绑定
    @Bean
    public Binding orderBinding(){
        return new Binding("order-queue", Binding.DestinationType.QUEUE,
                "order-exchange","createOrder",null);
    }
}
