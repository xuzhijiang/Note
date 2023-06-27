package springboot.security.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//就相当于springmvc.xml文件
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 访问/,就会被重定向到/login-view这个url
        //registry.addViewController("/").setViewName("redirect:/login-view");

        // 访问/login-view这个url,就会使用login这个视图进行渲染
        registry.addViewController("/login-view").setViewName("login");
    }
}
