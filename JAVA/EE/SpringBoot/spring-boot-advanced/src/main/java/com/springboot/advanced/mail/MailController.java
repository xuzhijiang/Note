package com.springboot.advanced.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Controller
public class MailController {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	AmqpTemplate amqpTemplate;

	@GetMapping("/")
	public String index() {
		return "mail_template";
	}

	@GetMapping("/sendSimpleMailMsg")
	@ResponseBody
	public String sendMethodTwo() {
		// 简单邮件
		SimpleMailMessage message = new SimpleMailMessage();
		// 邮件主题.
		message.setSubject("这是你2019年的全年消费账单,请查阅!!");
		// 邮件内容.
		message.setText("2019年全年消费总额为100元!!");
		// 注意这里配置的From要和application.yml中的spring.mail.username要一致
		message.setFrom("2233835996@qq.com");
		message.setTo("2233835996@qq.com");
		mailSender.send(message);
		return "邮件已发送成功,请查收!!";
	}

	ObjectMapper mapper = new ObjectMapper();

	@PostMapping("/sendToMQ")
	@ResponseBody
	public String send(@RequestParam("to") String to, @RequestParam("subject") String subject,
			@RequestParam("body") String body) throws IOException {
		Mail mail = new Mail();
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setBody(body);
		mail.validate();
		String message = mapper.writeValueAsString(mail);
		amqpTemplate.convertAndSend(MailConfig.EXCHANGE, MailConfig.QUEUE_NAME, message);
		return "<h3>Pending send.</h3>";
	}

	@GetMapping(value = "/sendAttachmentsMail")
	@ResponseBody
	public String sendAttachmentsMail() throws Exception {
		//1、创建一个复杂的消息邮件
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		// true: 表示要上传附件
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setSubject("2019年你的全年消费账单,请查收 [有附件]");
		// true: 是否为html
		helper.setText("<b style='color:red'>账单总金额为: 100元</b>", true);
		helper.setTo("2233835996@qq.com");
		helper.setFrom("2233835996@qq.com");
		// 添加附件
		helper.addAttachment("附件-1.jpg", new File("C:\\Users\\xu\\Desktop\\th.jpg"));
		mailSender.send(mimeMessage);
		return "发送带有附件的邮件发生成功";
	}

	@GetMapping(value = "/sendInlineMail")
	@ResponseBody
	public String sendInlineMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setSubject("2019年你的全年消费账单,请查收 [有附件]");
		// cid: contentId
		helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);
		helper.setTo("2233835996@qq.com");
		helper.setFrom("2233835996@qq.com");

		FileSystemResource file = new FileSystemResource(new File("C:\\Users\\xu\\Desktop\\th.jpg"));
		helper.addInline("weixin", file);
		mailSender.send(mimeMessage);
		return "sendInlineMail";
	}
}
