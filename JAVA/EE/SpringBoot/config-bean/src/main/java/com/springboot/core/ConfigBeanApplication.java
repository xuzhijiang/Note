package com.springboot.core;

import com.springboot.core.config.ConfigProperties;
import com.springboot.core.config.ELConfig;
import com.springboot.core.profile.IProfileBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ConfigBeanApplication {

	@Autowired
	UrlFetcher urlFetcher;

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(ConfigBeanApplication.class, args);

		testSpringEL(context);

		testExtractInfoFromPropertyFileByPrefix(context);

		testProfile(context);
	}

	private static void testProfile(ApplicationContext context) {
		//依据spring.profiles.active的值，实例化不同的Bean
		IProfileBean profileBean = context.getBean(IProfileBean.class);
		System.out.println(profileBean);
	}

	//测试从application.properties中提取信息构建相应的配置对象
	private static void testExtractInfoFromPropertyFileByPrefix(ApplicationContext context) {
		ConfigProperties myProperties = context.getBean(ConfigProperties.class);
		System.out.println(myProperties);
	}

	//演示Spring表达式
	private static void testSpringEL(ApplicationContext context) throws IOException {
		ELConfig config = context.getBean(ELConfig.class);
		config.printFields();
	}

}
