package spring.security.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 本项目使用slf4j日志框架门面,具体日志框架实现是使用Logback
 *
 * the Spring Boot chose to use Logback for the default logger.我们无需在项目中添加logback依赖,
 * 因为springboot-start已经默认添加.
 *
 * (注意springboot源码开发使用的是commons logging日志框架,和这个logback不是一回事,
 * 这里说的是普通用户项目开发的时候默认使用logback,而不是源码开发.不要混淆)
 *
 * 下面我们针对SLF4J API编写了日志代码。 SLF4J是常用日志框架的外观(例如Java Util Logging，Log4J 2和Logback),
 * 通过针对SLF4J进行编写，我们的代码与Logback分离，从而为我们提供了插入不同日志框架的灵活性(如果以后需要的话)
 *
 * https://javadeveloperzone.com/spring-boot/spring-boot-slf4j-and-logback-example/
 * https://springframework.guru/using-logback-spring-boot/
 *
 * https://www.jianshu.com/p/1fa12b92d5c4 (好的说明)
 */
@SpringBootApplication
public class SecureApplication {

	private final static Logger logger = LoggerFactory.getLogger(SecureApplication.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SecureApplication.class, args);
		logger.debug("Main App is starting.......");
		logger.info("Main App is starting.......");
		logger.warn("Main App is starting.......");
		logger.error("Main App is starting.......");
	}
}
