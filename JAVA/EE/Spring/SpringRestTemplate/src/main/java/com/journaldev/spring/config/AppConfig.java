package com.journaldev.spring.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

// 我们必须为RestTemplate类定义一个spring bean，that’s done in AppConfig class.
// (它在AppConfig类中完成。)

// 请注意(Note that): RestTamplate使用MessageConverter，
// 我们需要在RestTemplate bean中设置此属性。 
// 在我们的示例中，我们使用MappingJacksonHttpMessageConverter从JSON格式(fetching data)获取数据。

@Configuration
@ComponentScan("com.journaldev.spring")
public class AppConfig {

	@Bean
	RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		restTemplate.getMessageConverters().add(converter);
		return restTemplate;
	}
}
