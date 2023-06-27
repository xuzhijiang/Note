package com.springboot.web.restful.curd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@SpringBootApplication
public class SpringBootWebRestfulCrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebRestfulCrudApplication.class, args);
    }

    // 给DispatcherServer的doDispatch方法打断点,然后看看我们自己的视图解析器是否添加到了
    // ContentNegotiatingViewResolver中
    @Bean
    public ViewResolver myViewResolver() {
        return new MyViewResolver();
    }

    private static class MyViewResolver implements ViewResolver {

        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }
}
