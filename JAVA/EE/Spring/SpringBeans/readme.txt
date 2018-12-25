Spring Framework基于Inversion of Control原理。 
Dependency injection是在应用程序中实现IoC的技术(technique)。


Spring IoC Container

	Spring IoC是实现对象依赖关系之间松散耦合的机制。要在运行时实现对象的松散耦合和动态绑定，
	对象依赖项将由其他assembler对象注入。 
	
	Spring IoC容器是将依赖项(dependencies)注入对象并使其准备好可供我们使用的程序。
	
	我们已经了解了如何使用Spring Dependency Injection在我们的应用程序中实现IoC。

	Spring IoC容器类是org.springframework.beans和org.springframework.context包的一部分。
	Spring IoC容器为我们提供了 "解耦对象依赖关系" 的不同方法。

	BeanFactory是Spring IoC容器的根接口(root interface)。
	ApplicationContext是BeanFactory接口的子接口，提供Spring AOP功能，i18n等。

ApplicationContext的一些有用的子接口是ConfigurableApplicationContext和
WebApplicationContext。 Spring Framework提供了许多有用的ApplicationContext实现类，
我们可以使用它们来获取spring上下文(context)，然后是Spring Bean。

	我们使用的一些有用的ApplicationContext实现是:

		AnnotationConfigApplicationContext：如果我们在独立的Java应用程序中使用Spring并
		using annotations for Configuration，那么我们可以使用它来初始化容器并获取bean对象。
		
		ClassPathXmlApplicationContext：如果我们在独立应用程序中有spring bean配置xml文件，
		那么我们可以使用这个类加载文件并获取容器对象。
		
		FileSystemXmlApplicationContext：这类似于ClassPathXmlApplicationContext，
		除了可以从文件系统中的任何位置加载xml配置文件。
		
		AnnotationConfigWebApplicationContext和XmlWebApplicationContext。
