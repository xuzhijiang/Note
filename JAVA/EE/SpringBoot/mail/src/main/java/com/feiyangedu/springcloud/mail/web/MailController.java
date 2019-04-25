package com.feiyangedu.springcloud.mail.web;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feiyangedu.springcloud.mail.MailApplication;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Queue for sending mail.
 */
@Controller
public class MailController {

	final Log log = LogFactory.getLog(getClass());

	@Value("${spring.mail.username}")
	String from;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	AmqpTemplate amqpTemplate;

	ObjectMapper mapper = new ObjectMapper();

	@GetMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@PostMapping("/send")
	@ResponseBody
	public String send(@RequestParam("to") String to, @RequestParam("subject") String subject,
			@RequestParam("body") String body) throws IOException {
		Mail mail = new Mail();
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setBody(body);
		mail.validate();
		String message = mapper.writeValueAsString(mail);
		amqpTemplate.convertAndSend(MailApplication.EXCHANGE, MailApplication.QUEUE_NAME, message);
		return "<h3>Pending send.</h3>";
	}

	@GetMapping("/sendTo")
	@ResponseBody
	public String sendMethodTwo() {
		SimpleMailMessage message = new SimpleMailMessage();
		// 注意这里配置的From要和application.yml中的spring.mail.username要一致
		message.setFrom("2233835996@qq.com");
		message.setTo("2233835996@qq.com");
		message.setSubject("测试邮件（邮件主题）");//邮件主题.
		message.setText("这是邮件内容");//邮件内容.
		mailSender.send(message);
		return "send message successfully!!";
	}

	@RabbitListener(queues = MailApplication.QUEUE_NAME)
	public void processQueueMessage(String content) {
		try {
			Mail mail = mapper.readValue(content, Mail.class);
			log.info(String.format("sending mail to %s: subject=%s", mail.getTo(), mail.getSubject()));
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(this.from);
			helper.setTo(mail.getTo());
			helper.setSubject(mail.getSubject());
			helper.setText(mail.getBody());
			mailSender.send(message);
			log.info("mail sent successfully.");
		} catch (IOException | MessagingException e) {
			log.warn("mail sent failed.", e);
		}
	}

}
