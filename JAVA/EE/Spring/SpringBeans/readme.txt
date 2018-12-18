Spring Framework基于Inversion of Control原理。 
Dependency injection是在应用程序中实现IoC的技术(technique)。

Spring IoC Container

Spring IoC是实现对象依赖关系之间松散耦合的机制。要在运行时实现对象的松散耦合和动态绑定，
对象依赖项将由其他assembler对象注入。 Spring IoC容器是将依赖项(dependencies)注入对象
并使其准备好可供我们使用的程序。
我们已经了解了如何使用Spring Dependency Injection在我们的应用程序中实现IoC。

Spring IoC容器类是org.springframework.beans和org.springframework.context包的一部分。
Spring IoC容器为我们提供了 "解耦对象依赖关系" 的不同方法。

BeanFactory是Spring IoC容器的根接口(root interface)。 ApplicationContext是
BeanFactory接口的子接口，提供Spring AOP功能，i18n等。

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

通常，如果您正在使用Spring MVC应用程序并且您的应用程序使用Spring Framework配置的.
则Spring IoC容器会在应用程序启动时初始化，并且在请求Bean时，会自动注入依赖项。

但是，对于独立应用程序，您需要在应用程序中的某个位置初始化容器，然后使用它来获取spring bean。

Spring Bean:

Spring Bean没什么特别之处(Spring Bean is nothing special,)，
我们通过Spring container 初始化的Spring framework中的任何对象都称为Spring Bean。
任何普通的Java POJO类如果它被配置为通过容器通过提供配置元数据信息来初始化都可以是Spring Bean

Spring Bean Scopes

为Spring Beans定义了五个范围:

1. singleton  - 每个容器只创建一个bean实例。这是spring bean的默认范围。使用此范围时，
请确保bean没有共享实例变量，否则可能导致数据不一致问题。

2. prototype  - 每次请求bean时都会创建一个新实例。

3. request - 这与原型范围相同，但它意味着用于Web应用程序。将为每个HTTP请求创建一个新的bean实例。

4. session  - 将通过容器为每个HTTP session创建一个新bean。

5. global-session  - 用于为Portlet应用程序创建全局session bean。

Spring Framework是可扩展的，我们也可以创建自己的范围。但是，
大多数情况下，我们对框架提供的范围很满意。

Spring Bean Configuration:

Spring Framework提供了三种配置bean方法,以便在application中使用:

1. Annotation Based Configuration (基于注释的配置 )- 使用@Service或@Component annotations。
可以使用@Scope annotations提供范围详细信息。

2. XML Based Configuration基于XML的配置 - 通过创建Spring配置XML文件来配置bean。
如果您使用的是Spring MVC框架，则可以通过在web.xml文件中编写一些样板代码来自动加载基于xml的配置。

3. (Java Based Configuration)基于Java的配置 - 从Spring 3.0开始，我们可以使用java程序配置Spring bean。
用于基于java的配置的一些重要注释是@Configuration，@ ComponentScan和@Bean。

