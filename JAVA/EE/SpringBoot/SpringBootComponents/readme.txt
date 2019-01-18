## Spring Boot Framework的关键组件和内部组件

我们将讨论“Spring Boot的主要组件是什么”和“Spring Boot如何在引擎盖下工作”。

### Spring Boot Framework的关键组件

Spring Boot Framework主要有四个主要组件:

1. Spring Boot Starters
2. Spring Boot AutoConfigurator
3. Spring Boot CLI
4. Spring Boot Actuator

注意：除了这四个主要组件之外，还有两个Spring Boot组件：

* Spring Initilizr
* Spring Boot IDE

要快速启动新的Spring Boot项目，我们可以使用“Spring Initializr”Web界面。 Spring Initializr URL：http：//start.spring.io

我们有许多Spring Boot IDEs，如Eclipse IDE，IntelliJ IDEA，Spring STS Suite等。我们将在即将发布的帖子中讨论这两个组件。

   CLI               AutoConfigurator
     \               /
      \             /
     Spring Boot Components
     /               \
    /                 \
   Actuator          Starter

#### Spring Boot Starter

Spring Boot Starters是Spring Boot Framework的主要关键特性或组件之一。 Spring Boot Starter的主要职责是将一组公共或相关的依赖项组合成单个依赖项。我们将通过一个示例详细探讨此声明。

例如，我们希望使用Tomcat WebServer开发Spring WebApplication。然后我们需要在Maven的pom.xml文件或Gradle的build.gradle文件中添加以下最小jar依赖项:

1. Spring core Jar file(spring-core-xx.jar)
2. Spring Web Jar file(spring-web-xx.jar)
3. Spring Web MVC Jar file(spring-webmvc-xx.jar)
4. Servlet Jar file(servlet-xx.jar)

如果我们想添加一些数据库内容，那么我们需要添加数据库相关的jar，如Spring JDBC jar文件，Spring ORM jar文件，Spring Transaction Jar文件等:

5. Spring JDBC Jar文件（spring-jdbc-xx.jar）
6. Spring ORM Jar文件（spring-orm-xx.jar）
7. Spring Transaction Jar文件（spring-transaction-xx.jar）

我们需要在构建文件中定义很多依赖项。对于开发人员来说，这是非常繁琐和繁琐的任务。它还增加了我们的构建文件大小。

在我们的构建文件中避免这么多依赖项定义的解决方案是什么？解决方案是Spring Boot Starter component

Spring Boot Starter组件将所有相关的jar组合成单个jar文件，以便我们只添加这个jar文件依赖项到我们的构建文件中即可。我们需要添加一个且只有一个jar文件：“spring-boot-starter-web” jar文件，而不是将以上4个jars文件添加到我们的构建文件中。

当我们将“spring-boot-starter-web”jar文件依赖项添加到我们的构建文件中时，Spring Boot Framework将自动下载所有必需的jar并添加到我们的项目classpath中。

                                                 ---> spring-boot
                                                 |
                           -->spring-boot-starter--->spring-boot-autoconfigure
                          |                      |
                          |                       ->spring-boot-starter-logging
                          |
                          |------> spring-web                
spring-boot-starter-web---|
                          |
                          |------> spring-webmvc
                          |
                          |                              ->Tomcat-embed-core
                          |-->spring-boot-starter-tomcat-|
                                                         ->Tomcat-embed-logging-juli    

以同样的方式，“spring-boot-starter-logging”jar文件将所有它的依赖jar,如“jcl-over-slf4j，jul-to-slf4j，log4j-over-slf4j，logback-classic” 加载到我们的项目classpath中

##### Spring Boot Starter的主要优点

1. Spring Boot Starter减少了定义许多依赖项，简化了项目构建依赖性。
2. Spring Boot Starter简化了项目构建依赖性。

> 这是关于Spring Boot Starter组件的。我们将在即将发布的帖子中讨论一些Spring Boot示例的更多细节。

#### Spring Boot AutoConfigurator

Spring Boot Framework的另一个重要关键组件是Spring Boot AutoConfigurator。大多数Spring IO平台（Spring Framework）批评者的观点是“开发基于Spring的应用程序需要大量配置（注释配置的XML配置）。那怎么解决这个问题呢。

这个问题的解决方案是Spring Boot AutoConfigurator。 Spring Boot AutoConfigurator的主要职责是减少Spring配置。如果我们在Spring Boot中开发Spring应用程序，那么我们不需要定义单个XML配置，几乎没有或只有最小的Annotation配置。 Spring Boot AutoConfigurator组件将负责提供这些信息。

例如，如果我们想使用Spring IO Platform声明一个Spring MVC应用程序，那么我们需要定义很多XML配置，如views,view resolvers等。但是如果我们使用Spring Boot Framework，那么我们不需要定义那些XML配置。 Spring Boot AutoConfigurator将会做这些工作.

如果我们在项目构建文件中使用“spring-boot-starter-web”jar文件，那么Spring Boot AutoConfigurator将自动解析views, view resolvers等。

而且Spring Boot还减少了Annotation配置的定义。如果我们at class level(在类级别)使用@SpringBootApplication批注，那么Spring Boot AutoConfigurator会自动将所有必需的annotation添加到Java Class ByteCode。

@SpringBootApplication = @Configuration + @ComponentScan + @EnableAutoConfiguration

如果我们浏览Spring Boot 文档，我们可以找到@SpringBootApplication的以下定义:

```java
@Target(value=TYPE)
@Retention(value=RUNTIME)
@Documented
@Inherited
@Configuration
@EnableAutoConfiguration
@ComponentScan
public @interface SpringBootApplication
```

That is, @SpringBootApplication = @Configuration + @ComponentScan + @EnableAutoConfiration.

这就是Spring Boot AutoConfigurate组件。我们将在即将发布的帖子中讨论一些Spring Boot示例的更多细节。

注意：-

1. 简单来说，Spring Boot Starter减少了构建的依赖性，Spring Boot AutoConfigurator减少了Spring配置。
2. 正如我们所讨论的，Spring Boot Starter依赖于Spring Boot AutoConfigurator，Spring Boot Starter会自动触发Spring Boot AutoConfigurator

#### Spring Boot CLI

Spring Boot CLI（Command Line Interface-命令行界面）是一个Spring Boot软件，用于从命令提示符运行和测试Spring Boot应用程序。当我们使用CLI运行Spring Boot应用程序时，它在内部使用Spring Boot Starter和Spring Boot AutoConfigurate组件来解析所有依赖项并执行应用程序。

我们可以使用简单的Spring Boot CLI命令运行Spring Web应用程序。

Spring Boot CLI引入了一个新的“spring”命令，用于从命令提示符执行Groovy Scripts。

spring command example：

```shell
spring run HelloWorld.groovy
```

这里HelloWorld.groovy是一个Groovy脚本FileName。与Java源文件名有*.java扩展名一样，Groovy脚本文件具有*.groovy扩展名。 “spring”命令执行HelloWorld.groovy并生成输出。

Spring Boot CLI组件需要许多步骤，如 CLI Installation, CLI Setup，开发简单的Spring Boot应用程序并对其进行测试。因此，我们将专门发布另一篇文章，详细讨论一些Spring Boot示例

#### Spring Boot Actuator

Spring Boot Actuator组件提供了许多功能，但两个主要功能是：

1. Providing Management EndPoints to Spring Boot Applications.
2. Spring Boot Applications Metrics.

当我们使用CLI运行Spring Boot Web应用程序时，Spring Boot Actuator会自动将主机名提供为“localhost”，默认端口号为“8080”。我们可以使用“http：// localhost：8080/”端点来访问此应用程序。

我们实际上使用GET和POST等HTTP Request方法来表示使用Spring Boot Actuator的Management EndPoints。

我们将在即将发布的帖子中讨论有关Spring Boot Actuator的更多细节。

### Internals of Spring Boot Framework - Spring Boot Framework的内部结构

总是建议理解Spring Boot Framework如何减少构建的依赖性，Spring配置等.Spring Boot如何工作。

如果您熟悉Groovy编程语言，那么您就知道了大部分内容。在Groovy中，我们不需要添加一些导入，也不需要为Groovy项目添加一些依赖项。当我们使用Groovy Compiler（groovyc）编译Groovy脚本时，它会自动添加所有默认的import语句然后编译它。

同样，Groovy Programming语言包含一个JAR依赖关系解析器，用于解析并将所有必需的jar文件添加到Groovy Project classpath中。

Spring Boot Framework在内部使用Groovy添加一些默认值，如Default import语句，Application main（）方法等。当我们从CLI命令提示符运行Groovy Scripts时，它使用此main（）方法运行Spring Boot Application

#### Grape-葡萄

Grape是一个嵌入式依赖性解析引擎。 Grape是嵌入到Groovy中的JAR依赖管理器。 Grape允许我们快速将maven存储库依赖项添加到项目classpath中以减少构建文件定义。

Spring Boot Framework编程模型主要受Groovy Programming模型的启发。 Spring Boot Framework内部依赖于这两个主要组件：Groovy和Grape。

                    ----> Groovy
    springboot----->
                    ----> Grovvy's Grap

有关详细信息，请参阅Grape文档http://docs.groovy-lang.org/latest/html/documentation/grape.html。

这是关于Spring Components and Internals.