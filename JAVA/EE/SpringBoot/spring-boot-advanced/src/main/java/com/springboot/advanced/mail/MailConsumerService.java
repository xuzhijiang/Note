package com.springboot.advanced.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class MailConsumerService {

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String from;

    ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = MailConfig.QUEUE_NAME)
    public void processQueueMessage(String content) {
        try {
            Mail mail = mapper.readValue(content, Mail.class);
            System.out.println(String.format("sending mail to %s: subject=%s", mail.getTo(), mail.getSubject()));
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(this.from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody());
            mailSender.send(message);
            System.out.println("mail sent successfully.");
        } catch (IOException | MessagingException e) {
            System.out.println("mail sent failed: " + e.getMessage());
        }
    }

}
