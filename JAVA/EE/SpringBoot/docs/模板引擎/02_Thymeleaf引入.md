# thymeleaf

    直接打开thymeleaf的html页面会展现默认的信息，但是经过渲染后,会展示后台添加的信息,
    做到了数据和后台业务逻辑,所以比jsp好.非常有利于前后端的分离

# 1、引入thymeleaf

```xml
<project>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <!-- springboot默认配置的thmeleaf的版本可能比较低,所以要切换thymeleaf版本 -->
    <properties>
        <thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
        <!-- 布局功能的支持,layout2以上版本才能支持thymeleaf3 -->
        <!-- layout1支持的是thymeleaf2-->
        <thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
    </properties>
</project>
```

# Thymeleaf的相关的属性

~~~java
@ConfigurationProperties(prefix = "spring.thymeleaf")
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");
    private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");
    // 默认情况下， Thymeleaf从classpath:/templates/处加载模板.
    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
  //只要我们将HTML页面存放在classpath:/templates/目录中，thymeleaf就能自动渲染
}
~~~
