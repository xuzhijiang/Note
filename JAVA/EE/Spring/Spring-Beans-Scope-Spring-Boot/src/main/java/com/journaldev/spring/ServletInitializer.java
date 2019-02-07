package com.journaldev.spring;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// "ServletInitializer"和"SpringBootMvcApplication"是自动生成的spring引导类。 我们不需要在那里做任何改变。
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootMvcApplication.class);
	}

}