package com.springboot.web.restful.curd.config;

import com.springboot.web.restful.curd.interceptor.LoginHandlerInterceptor;
import com.springboot.web.restful.curd.component.MyLocaleResolver;
import com.springboot.web.restful.curd.interceptor.WebStopWatch;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//使用WebMvcConfigurer可以来扩展SpringMVC的功能
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    // 配置嵌入式的Servlet容器
    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {
        // 要用接口,不要用具体的实现类
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            // 定制嵌入式的Servlet容器 的相关属性
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                // 和在配置文件中添加server.port=8088效果一样
                factory.setPort(8083);
            }
        };
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/mvc_config_success").setViewName("success");
    }

    // 所有的WebMvcConfigurer组件都会一起起作用
    @Bean //将组件注册在容器
    public WebMvcConfigurer webMvcConfigurer() {
        WebMvcConfigurer webMvcConfigurer =  new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        // 排除不拦截的路径,/**: 任意多层路径下的任意请求.
                        // 这里排除登录页面,以及index.html, 以及静态资源
                        .excludePathPatterns("/", "/index.html", "/user/login", "/asserts/**", "/webjars/**");

                registry.addInterceptor(new WebStopWatch());
            }
        };

        return webMvcConfigurer;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
// 不要使用@EnableWebMvc,因为这会导致springboot的自动配置失效
// 因为如果使用@EnableWebMvc,就会创建WebMvcConfigurationSupport的实例,
// 如果存在WebMvcConfigurationSupport 这个类的bean,就会导致 springboot的 自动配置类WebMvcAutoConfiguration失效