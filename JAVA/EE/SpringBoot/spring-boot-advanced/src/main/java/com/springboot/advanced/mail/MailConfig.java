package com.springboot.advanced.mail;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * static/img下列出常见的几个邮件服务器的相关信息。因为我们是发送邮件，只需要关注smtp协议即可。
 *
 * 163邮箱的收取邮件支持POP/IMAP两种协议，发送邮件采用SMTP协议，收件和发件均使用SSL协议来进行加密传输，
 * 采用SSL协议需要单独对帐户进行设置。采用SSL协议和非SSL协议时端口号有所区别
 */
@Configuration
public class MailConfig {

    // Queue constants:
    public static final String QUEUE_NAME = "mail";
    public static final String EXCHANGE = "exchange";

    @Bean
    public Queue queue() {
        // durable = false, autoDelete = true
        return new Queue(QUEUE_NAME, false, false, true);
    }

    @Bean
    TopicExchange exchange() {
        // durable = false, autoDelete = true
        return new TopicExchange(EXCHANGE, false, true);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        /**
         * 自定义我们自己的静态资源文件映射，因为springboot默认的静态资源映射 不利于nginx反向代理配置
         */
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
            }
        };
    }
}
