package org.mq.core.server.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean("message")
    public Queue queue() {
        return new Queue("sam.message",true,false,false,null);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange.message", true,false,null);
    }

    @Bean
    public Binding bindingExchangeQueue(@Qualifier("message") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("sam.message.routeKey");
    }
}
