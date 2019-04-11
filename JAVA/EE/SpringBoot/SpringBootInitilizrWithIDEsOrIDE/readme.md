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