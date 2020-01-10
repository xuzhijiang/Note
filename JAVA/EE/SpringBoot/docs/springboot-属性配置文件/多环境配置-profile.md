# 多环境支持方式一

    配置文件名带上不同环境的标识: application-{profile}.yml,如application-prod.yml
    
    创建配置文件application.yml、application-dev.yml、application-pro.yml
    
    程序默认使用application.yml，在application.yml中使用spring.profiles.active=dev来激活指定的配置文件

# 多环境支持方式二

    springboot项目中yaml中可以定义多文档块方式，然后指定激活的profile

~~~yaml
server:
  port: 8080
spring:
  profiles:
    active: dev # 激活指定的文档块

---
server:
  port: 8081
spring:
  profiles: dev # Define profile for this document 为这个文档块定义,定义它属于什么profile


---
server:
  port: 8082
spring:
  profiles: prod

---
server:
  port: 8083
spring:
  profiles: test
~~~

# 多环境支持方式三

    命令行(不同于虚拟机参数): java -jar xx.jar --spring.profiles.active=dev
    
    虚拟机参数(VM Options)；-Dspring.profiles.active=dev

# 多环境支持方式四

    还可以使用Profile注解，MyConfig只会在prod环境下才会生效，其他profile不会生效

```java
@Profile("prod")
@Configuration
public class MyConfig {}
```