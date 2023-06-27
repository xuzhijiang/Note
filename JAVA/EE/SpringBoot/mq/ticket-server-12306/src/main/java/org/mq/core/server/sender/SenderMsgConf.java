package org.mq.core.server.sender;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 12306把处理结果发送给美团
@Configuration
public class SenderMsgConf {

    @Bean("ticket-message")
    public Queue queue() {
        return new Queue("sam.ticket.response",true,false,false,null);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("ticket-exchange.response", true,false,null);
    }

    @Bean
    public Binding bindingExchangeQueue(@Qualifier("ticket-message") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("sam.ticket.routeKey.response");
    }
}
