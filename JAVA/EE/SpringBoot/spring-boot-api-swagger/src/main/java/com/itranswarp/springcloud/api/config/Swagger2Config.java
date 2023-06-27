package com.itranswarp.springcloud.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //通过@EnableSwagger2注解来启动Swagger2.
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())// 用来创建该Api的基本信息
                .select() //select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现，本例采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被@ApiIgnore指定的请求）。
                .apis(RequestHandlerSelectors.basePackage("com.itranswarp.springcloud.api"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("我是标题")
                .description("我是描述")
                .termsOfServiceUrl("myServiceUrl")
                .contact("xxx-contact")
                .version("V2.0.1")
                .build();
    }

//    @Bean
//    public Docket userApi() {
//        return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
//    }
}
