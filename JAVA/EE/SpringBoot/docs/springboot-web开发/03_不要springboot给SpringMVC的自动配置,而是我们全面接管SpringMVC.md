# 全面接管SpringMVC

    我们要的效果就是: SpringBoot对SpringMVC的自动配置不需要了，所有都是我们自己配置；所有的SpringMVC的自动配置都失效了

    我们需要在配置类中添加@EnableWebMvc即可

```java
// 使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
@EnableWebMvc
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       // super.addViewControllers(registry);
        //浏览器发送 /atguigu 请求来到 success
        registry.addViewController("/atguigu").setViewName("success");
    }
}
```

# 为什么添加@EnableWebMvc之后,springboot自动配置就失效了?

```java
// @EnableWebMvc将WebMvcConfigurationSupport组件导入进来
@Import(DelegatingWebMvcConfiguration.class)
public @interface EnableWebMvc {
```

```java
@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
```

```java
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class,
		WebMvcConfigurerAdapter.class })
//容器中没有这个组件的时候，这个自动配置类才生效
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class,
		ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {
```

    WebMvcAutoConfiguration自动配置失效之后
    通过@EnableWebMvc导入的WebMvcConfigurationSupport只起到SpringMVC最基本的功能；
    @EnableWebMvc只导入了 WEbMvcConfigurationSupport最基本的功能,像视图解析器/静态资源映射/拦截器等都需要自己再配置.




