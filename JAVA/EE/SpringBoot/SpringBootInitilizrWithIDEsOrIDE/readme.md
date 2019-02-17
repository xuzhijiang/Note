## 简要内容

1. 使用IDEs/IDE插件的Spring Boot Initilizr
2. 使用IDEs/IDE插件执行Spring Boot应用程序
3. "SpringApplication"类的内部和其重要性

Spring Boot Initilizr用于快速启动新的Spring Boot Maven/Gradle项目。它生成初始项目结构并构建脚本以减少开发时间

之前讨论过Spring Boot Initilizr有以下几种形式：

1. Spring Boot Initilizr With Web Interface
2. Spring Boot Initilizr With IDEs/IDE Plugins
3. Spring Boot Initilizr With Spring Boot CLI
4. Spring Boot Initilizr With ThirdParty Tools

我们之前已经讨论过Spring Boot Initilizr With Web Interface。现在我们将讨论“如何将Spring Boot Initilizr与IDE/IDE插件一起使用”。几乎所有Java IDE都提供Spring Boot插件。

我们将使用Spring STS Suite开发所有Spring Boot程序。默认情况下，Spring STS Suite附带Spring Boot Plugins，无需安装/配置它。

### Spring Boot Initilizr With IDEs/IDE Plugins

请按照以下步骤使用Spring STS Suite创建Spring MVC Maven项目。(与其他IDE几乎相似的步骤。）

File -> New -> Spring Stater Project

```
Name: SpringMVCMavenIDEProject
Packing: War 
Group: com.journaldev
Artifact: SpringMVCMavenIDEProject
Package: com.journaldev.springboot.sample
```

选择我们的项目相关技术堆栈，如下所示:

```
Boot Version: 选择稳定的版本
Data: JPA
Database: H2
I/O: JMS
Web: WS, Web
```

现在，Spring STS Suite创建了一个Maven项目，并将所有必需的Jars下载到我们的Maven Local Repository。

以同样的方式，我们可以创建JARs，Gradle项目，Spring Web Services, Spring REST API, Spring Cloud etc.

#### Execute Spring Boot Application

使用Spring STS IDE创建了Spring Boot WebApplication。是时候运行这个应用程序并观察结果。

鼠标右键单击“SpringMvcMavenIdeProjectApplication.java”文件并从“运行方式”菜单中选择“Spring Boot App”选项以将此程序作为Spring Boot Application运行.

注意： - 为什么我们需要运行“SpringMvcMavenIdeProjectApplication.java”程序？
因为“SpringMvcMavenIdeProjectApplication.java”程序有Java的main()方法。我们很快就会看到“SpringApplication.run()”方法的重要性。

当我们执行“SpringMvcMavenIdeProjectApplication.java”程序时，Spring Boot会自动执行以下操作：

1. 创建一个Spring MVC应用程序
2. 使用默认端口号启动嵌入式Tomcat服务器：8080
3. 将我们的Spring MVC应用程序部署到Tomcat Sever中

使用“http://localhost：8080/SpringMVCMavenIDEProject”
或“http://localhost:8080/*”访问我们的Spring MVC应用程序并观察结果,
这里*表示任何URI或上下文路径.

由于我们没有提供任何映射或View Pages(ex:- JSPs), 因此我们看到了默认错误页面。

注意：-

1. 使用此基础Spring Boot WebApplication项目并根据您的项目要求进行增强。
2. 我们还可以使用Spring STE IDE生成的“SpringMvcMavenIdeProjectApplicationTests.java”类来执行和测试我们的Spring Boot应用程序
3. 鼠标右键单击“SpringMvcMavenIdeProjectApplicationTests.java”类并选择“Run As >> JUnit Test”选项

### Internals and Importance of “SpringApplication” class

现在，我们将讨论什么是SpringApplication？为什么我们需要这个？ SpringApplication的重要性？ SpringApplication的内部结构？

1. “SpringApplication”是Spring Boot API classes之一
2. SpringApplication类用于引导一个从“main()”方法启动的Spring应用程序

	a. Spring STS IDE生成的类：“SpringMvcMavenIdeProjectApplication”
	b. 我们从SpringMvcMavenIdeProjectApplication类的main()方法调用SpringApplication.run()

3. SpringMvcMavenIdeProjectApplication类使用@SpringBootApplication批注来注解,
`@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan`

	@SpringBootApplication执行以下操作：

	a. 由于@Configuration注释，它会扫描@Bean方法来创建beans
	b. 由于@ComponentScan注释，它执行组件扫描(Components表示使用@Component，@Service，@Repository，@Controller等注释的Bean）。
	c. 由于@EnableAutoConfiguration注释，它会触发Spring Boot Auto-Configuration

4. 默认情况下，SpringApplication类创建“AnnotationConfigEmbeddedWebApplicationContext”实例.

						EmbeddedWebAnnotationContext
									^
									|
						|											|
AnnotationConfigEmbeddedWebApplicationContext  XmlEmbeddedWebApplicationContext

			Spring Boot WebApplicationContext API

5. “AnnotationConfigEmbeddedWebApplicationContext”实例在Project Classpath中搜索JAR文件。基于在Classpath中找到的JAR，它创建了一个实现类“EmbeddedServletContainerFactory”接口的实例.

						EmbeddedServletContainerFactory
									^
									|
			|												|
TomcatEmbeddedServletContainerFactory	JettyEmbeddedServletContainerFactory
				spring boot EmbeddedServletContainerFactory API

6. 默认情况下，Spring STS IDE将所有必需的Tomcat容器JAR添加到我们新创建的Spring Boot Project Classpath中。这就是“AnnotationConfigEmbeddedWebApplicationContext”实例创建“TomcatEmbeddedServletContainerFactory”类实例的原因。

7. “EmbeddedServletContainerFactory”(“JettyEmbeddedServletContainerFactory”或“TomcatEmbeddedServletContainerFactory”）用于创建“EmbeddedServletContainer”实现的实例。

							EmbeddedServletContainer
									^
									|
		|										|
TomcatEmbeddedServletContainer 				JettyEmbeddedServletContainer
				spring boot EmbeddedServletContainer API

8. 默认情况下，Spring STS IDE会创建“TomcatEmbeddedServletContainerFactory”类的实例。该工厂用于创建“TomcatEmbeddedServletContainer”类的实例。

当我们运行SpringMvcMavenIdeProjectApplication类的main()方法时，它会调用“SpringApplication.run()”方法。

Spring STS IDE中的“SpringApplication.run()”方法

	a. 此调用用于创建“AnnotationConfigEmbeddedWebApplicationContext”。
	b. 此“AnnotationConfigEmbeddedWebApplicationContext”实例用于创建“TomcatEmbeddedServletContainerFactory”类的实例。
	c. 这个“TomcatEmbeddedServletContainerFactory”用于创建“TomcatEmbeddedServletContainer”类的实例。
	d. TomcatEmbeddedServletContainer实例在默认端口8080处启动Tomcat容器并部署我们的Spring Boot WebApplication

这是关于使用IDEs/IDE插件的Spring Boot Initilizr以及“SpringApplication”类的重要性。