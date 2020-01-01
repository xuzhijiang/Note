# 多环境支持方式一

    配置文件名带上不同环境的标识: application-{profile}.yml,如application-prod.yml
    
    创建配置文件application.yml、application-dev.yml、application-pro.yml
    
    程序默认使用application.yml，在application.yml中使用spring.profiles.active=dev来激活指定的配置文件

# 多环境支持方式二

    springboot项目中yaml中可以定义多个profile，然后指定激活的profile

~~~yaml
server:
  port: 8080
spring:
  profiles:
    active: dev
---
server:
  port: 8081
spring:
  profiles: dev
---
server:
  port: 8084
spring:
  profiles: pro
~~~

# 多环境支持方式三

    在运行jar时执行指定: java -Dspring.profiles.active="prod" -jar xx.jar

# 多环境支持方式四

    还可以使用Profile注解，MyConfig只会在prod环境下才会生效，其他profile不会生效

```java
@Profile("prod")
@Configuration
public class MyConfig {}
```