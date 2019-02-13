spring-boot-starter：核心模块，包括自动配置支持、日志和YAML
spring-boot-starter-test：测试模块，包括JUnit、Hamcrest、Mockito

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
</dependencies>
```

引入Web模块，需添加spring-boot-starter-web模块：

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

application.properties主要用来配置数据库连接、日志相关配置等

我们直接在application.properties中定义一些自己使用的属性，然后通过@Value("${属性名}")注解来加载对应的配置属性:

```
com.didispace.blog.name=MyBlog
```

```java
@Component
public class BlogProperties {

    @Value("${com.didispace.blog.name}")
    private String name;

}
```

>在application.properties中的各个参数之间也可以直接引用来使用，就像下面的设置：com.didispace.blog.desc参数引用了上文中定义的name和title属性.

```
com.didispace.blog.name=name
com.didispace.blog.title=title
com.didispace.blog.desc=${com.didispace.blog.name} - 《${com.didispace.blog.title}》
```

使用随机数:

>在一些情况下，有些参数我们需要希望它不是一个固定的值，比如密钥、服务端口等。Spring Boot的属性配置文件中可以通过${random}来产生int值、long值或者string字符串，来支持属性的随机值(参考Chapter2-1-1)

>通过命令行设置属性值

相信使用过一段时间Spring Boot的用户，一定知道这条命令：java -jar xxx.jar --server.port=8888，通过使用–-server.port属性来设置xxx.jar应用的端口为8888。

在命令行运行时，连续的两个减号--就是对application.properties中的属性值进行赋值的标识。所以，java -jar xxx.jar --server.port=8888命令，等价于我们在application.properties中添加属性server.port=8888，该设置在样例工程中可见，读者可通过删除该值或使用命令行来设置该值来验证。(参考Chapter2-1-1)

通过命令行来修改属性值固然提供了不错的便利性，但是通过命令行就能更改应用运行的参数，那岂不是很不安全？是的，所以Spring Boot也贴心的提供了屏蔽命令行访问属性的设置，只需要这句设置就能屏蔽：SpringApplication.setAddCommandLineProperties(false)。

