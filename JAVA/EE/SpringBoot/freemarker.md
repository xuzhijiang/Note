pringBoot官方不建议在Springboot项目中使用JSP这样的技术，取而代之的是freemarker、velocity这样的模板引擎。

使用freemarker的时候，加入以下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

>然后在resources目录下建立一个templates目录即可，视图将会从这个templates位置开始找。