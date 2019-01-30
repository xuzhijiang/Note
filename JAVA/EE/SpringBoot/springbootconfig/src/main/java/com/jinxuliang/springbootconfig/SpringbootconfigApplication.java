package com.jinxuliang.springbootconfig;

import com.jinxuliang.springbootconfig.config.ELConfig;
import com.jinxuliang.springbootconfig.profile.IProfileBean;
import com.jinxuliang.springbootconfig.properties.MyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class SpringbootconfigApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext context=SpringApplication.run(SpringbootconfigApplication.class, args);

		//testSpringEL(context);

		testExtractInfoFromPropertyFileByPrefix(context);

		//testProfile(context);


	}

	private static void testProfile(ApplicationContext context) {
		//依据spring.profiles.active的值，实例化不同的Bean
		IProfileBean profileBean=context.getBean(IProfileBean.class);
		System.out.println(profileBean);
	}

	//测试从application.properties中提取信息构建相应的配置对象
	private static void testExtractInfoFromPropertyFileByPrefix(ApplicationContext context) {
		MyProperties myProperties=context.getBean(MyProperties.class);
		System.out.println(myProperties);
	}

	//演示Spring表达式
	private static void testSpringEL(ApplicationContext context) throws IOException {
		ELConfig config=context.getBean(ELConfig.class);
		config.printFields();
	}
}
