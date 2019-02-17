Spring Boot是Pivotal Team(The Spring Team）的一个全新项目。它是在现有Spring Framework之上开发的框架。

### Spring Boot教程

Spring Boot使用全新的开发模型，通过避免一些繁琐的开发步骤和样板代码和配置，使Java开发变得非常容易。

#### 什么是Spring Boot？

Spring Boot是一个来自“Spring Team”的框架，用于简化新Spring应用程序的引导和开发。
它提供了代码和注释配置的默认值，可以在短时间内快速启动新的Spring项目。它遵循“Opinionated Defaults Configuration-意见默认配置”方法，以避免大量样板代码和配置，以改进开发，单元测试和集成测试过程。

#### 什么不是Spring Boot？

Spring Boot Framework并非由Spring Team从头开始实现，而不是在现有的Spring Framework(Spring IO Platform）之上实现。
它不用于解决任何新问题。它用于解决类似Spring Framework的问题。

#### 为什么选择Spring Boot？

1. 简化基于Java的应用程序开发，单元测试和集成测试过程。
2. 通过提供一些默认值来减少开发，单元测试和集成测试时间。
3. 提高生产力。

在此阶段不要担心什么是“Opinionated Defaults Configuration-意见默认配置”方法。

#### Spring Boot的优点：

* 使用Java或Groovy开发基于Spring的应用程序非常容易。
* 它减少了大量的开发时间并提高了生产率。
* 它避免编写大量的样板代码，注释和XML配置。
* 将Spring Boot Application与Spring生态系统中的Spring JDBC，Spring ORM，Spring Data，Spring Security等集成在一起非常容易。
* 它遵循“Opinionated Defaults Configuration”方法来减少开发人员的工作量
* 它提供了嵌入式HTTP服务器，如Tomcat，Jetty等，可以非常轻松地开发和测试我们的Web应用程序。
* 它提供了CLI(Command Line Interface - 命令行界面）工具，可以非常轻松快速地从命令提示符开发和测试Spring Boot(Java或Groovy）应用程序。
* 它提供了许多插件，可以使用Maven和Gradle等构建工具轻松地开发和测试Spring Boot应用程序
* 它提供了许多插件，可以非常轻松地使用嵌入式和内存数据库。

In Simple Terminology, What Spring Boot means
在简单术语中，Spring Boot意味着什么

Spring Boot =  Spring Framework + Embedded HTTP Servers(Tomcat,Jetty) - XML <bean> Configuration or @Configuration

这意味着Spring Boot只是现有的Spring Framework + 一些嵌入式HTTP服务器(Tomcat/Jetty等）- XML或Annotations配置。

这里减去意味着我们不需要编写任何XML配置和少量注释。

#### Spring Boot的主要目标：

Spring Boot Framework的主要目标是减少开发，单元测试和集成测试时间，并且与现有的Spring框架相比，可以非常轻松地简化生产就绪Web的应用程序的开发，而现有的Spring框架确实需要更多时间:

1. 完全避免XML配置
2. 避免定义更多注释配置(它将一些现有的Spring Framework注释组合到一个简单的单个注释中）
3. 避免编写大量的import语句
4. 提供一些默认值，以便在短时间内快速启动新项目。
5. 提供意见发展方法(To provide Opinionated Development approach.)

通过提供或避免这些内容，Spring Boot Framework缩短了开发时间，缩短了开发人员的工作量并提高了工作效率

#### Limitation/Drawback of Spring Boot(Spring Boot的限制/缺点)：

Spring Boot Framework有一个限制。
将现有或传统的Spring Framework项目转换为Spring Boot应用程序是一个耗时的过程，但我们可以将各种项目转换为Spring Boot应用程序。使用Spring Boot创建全新/Greenfield项目非常容易。

要启动Opinionated方法来创建Spring Boot应用程序，Spring Team(Pivotal Team）提供了以下三种方法:

1. 使用Spring Boot CLI工具
2. 使用Spring STS IDE
3. 使用Spring Initializr网站

我们将在接下来的帖子中逐一详细讨论一些好的例子。我们可以在以下网址找到Spring Initializr网站：http：//start.spring.io/

我们可以使用Spring Boot开发两种基于Spring的应用程序:

1. 基于Java的应用程序
2. Groovy应用程序

我们可以使用Spring Boot CLI或Spring STS IDE或Spring Initializr Website来开发Spring Boot Groovy应用程序。
但是，我们可以使用Spring STS IDE或Spring Initializr Website来开发Spring Boot Java应用程序。

无论如何(Anyhow)，与Java几乎类似,Groovy也是一种JVM语言。我们可以将Groovy和Java结合到一个Project中。因为像Java文件一样，Groovy文件最终只能编译成*.class文件。 * .groovy和*.java文件都转换为*.class文件(相同的字节代码格式）：

Groovy(*.groovy) -----> 
                    Compiler (javac or groovyc) -----> *.class files(Byte Code)
Java(*.java)   ------->

Spring Boot Framework编程模型的灵感来自Groovy Programming模型。 Spring Boot内部使用一些基于Groovy的技术和工具来提供默认导入和配置。

Spring Boot Framework还将现有的Spring Framework注释组合成一些简单或单个注释。

Spring Boot Framework将基于Spring-Java的应用程序编程模型彻底改变为新的编程模型。 截至目前，Spring Boot仅处于初始阶段，但未来仅限于Spring Boot。
