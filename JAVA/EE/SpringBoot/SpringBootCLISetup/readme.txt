我们将选择一个Spring Boot组件：CLI,详细讨论它

### 什么是Spring Boot CLI？

Spring Boot CLI(命令行界面）是一个Spring Boot软件，用于从命令提示符运行和测试Spring Boot应用程序。当我们使用CLI运行Spring Boot应用程序时，它在内部使用Spring Boot Starter和Spring Boot AutoConfigurate组件来解析所有依赖项并执行应用程序。

它内部包含Groovy和Grape(JAR依赖管理器）以添加Spring Boot Defaults并自动解析所有依赖项。

我们将在Windows环境中讨论CLI安装，CLI Setup和CLI命令。它也与其他环境几乎相似。

#### Spring Boot CLI安装

我们可以使用Windows Installer或Zip文件安装Spring Boot CLI软件。这两种方法都易于安装，并且将为我们提供相同的Spring Boot CLI软件。我们将使用使用Zip文件的简单方法。我们将使用Spring Boot最新版本：1.2.3.RELEASE

我们可以在http://start.spring.io/下载Spring Boot CLI软件(它是一个Spring Initilizr Web Interface。我们将在后续帖子中详细讨论这个组件。）

请按照以下步骤在Windows环境中安装和设置Spring Boot CLI软件:

1. 使用Spring Initilizr下载Spring Boot CLI zip文件
单击“下载Spring CLI Zip”按钮，如下所示：

一旦我们将Spring Boot CLI Zip文件下载到我们的本地FileSystem中，它就像这样:spring-boot-cli-2.1.2.RELEASE-bin.zip

在Windows系统中设置Spring Boot CLI环境变量:

set PATH=D:\spring-boot-cli-1.2.3.RELEASE\bin;%PATH%

执行以下命令以验证我们的安装过程:

1. spring --help
2. spring --version

在讨论Spring Boot“HelloWorld”示例之前，首先我们将看看如何从命令提示符运行Groovy脚本。

##### Spring Boot“spring”命令

Spring Boot CLI软件提供了一个“spring”命令，用于从命令提示符运行Spring Boot Groovy脚本。正如我们之前看到的，Spring Boot“spring -help”命令有很多选项可以将此命令用于不同的目的。我们将在这里使用“run”选项

“spring” command syntax:

> spring run <SpringBoot-Groovy-Scriptname>

这是Spring Boot应用程序的Groovy Script文件名。我们将使用此命令执行Spring Boot HelloWorld示例。

#### Spring Boot HelloWorld示例

现在我们将开发一个Spring Boot MVC RestController示例。这是Pivotal团队发布的第一个演示Spring Boot Framework功能的示例。

请按照以下步骤开发Spring Boot HelloWorld示例：

1. 创建一个“HelloWorld”文件夹来放置我们的groovy脚本。
2. 使用以下内容开发Groovy脚本文件:

```java
@RestController
class HelloWorld {
  @RequestMapping("/")
  String hello() {
    "Hello JournalDev World."
  }
}
```

将此文件命名为HelloWorld.groovy。这里“.groovy”扩展是强制性的。

代码说明:

1. 使用Spring 4 MVC @RestController注释定义a REST Controller 
2. 使用Spring MVC @RequestMapping注释定义a Mapping URL “/”
3. 定义了hello method,将String返回到client或Web浏览器

如果我们观察HelloWorld.groovy，我们可以找到以下要点:

1. No imports(没有导入)
2. No other XML configuration to define Spring MVC Components like Views,ViewResolver etc.(没有其他XML配置来定义Spring MVC组件，如Views，ViewResolver等。)
3. No web.xml and No DispatcherServlet declaration(没有web.xml和No DispatcherServlet声明)
4. No build scripts to create our Application war file(没有用于创建Application war文件的构建脚本)
5. No need to build war file to deploy this application(无需构建war文件来部署此应用程序)

那么谁将为Spring Boot HelloWorld应用程序提供所有这些东西？首先运行应用程序并查看结果，然后我们将回答这个问题。

现在我们的Spring Boot HelloWorld示例已经准备好了Spring MVC RestController。是时候运行并测试这个例子来了解Spring Boot Framework的强大功能。

### Run Spring Boot HelloWorld Example

请按照以下步骤测试我们的Spring Boot HelloWorld示例应用程序：

1. 在我们的本地文件系统中的“HelloWorld”文件夹中打开命令提示符。
2. 执行以下命令: ` spring run HelloWorld.groovy`

观察“spring run”命令控制台的输出,当我们执行“spring run HelloWorld.groovy”时，它会在默认端口号：8080启动嵌入式Tomcat服务器。

这里o.s.boot.SpringApplication表示org.springframework.boot.SpringApplication类。
什么是SpringApplication？ SpringApplication有什么用？
请参阅我即将发布的帖子来回答这些问题。

`访问此URL：http://localhost:8080/`

现在我们可以访问我们的第一个Spring Boot MVC RESTful WebService。

如果我们观察这个Spring Boot应用程序，那么我们可能会想到这个问题：谁将为Spring Boot HelloWorld应用程序提供所有这些内容？

1. No imports
2. No other XML configuration to define Spring MVC Components like 3. Views,ViewResolver etc.
4. No web.xml and No DispatcherServlet declaration
5. No build scripts to create our Application war file
6. No need to build war file to deploy this application

答安:由Spring Boot Core Components，Groovy Compiler(groovyc）和Groovy Grape(Groovy的JAR Dependency Manager）负责。

Spring Boot Components使用Groovy Compiler和Groovy Grape提供一些Defaults lime添加所需的导入，提供所需的配置，解决jar依赖，添加main(）方法等。作为Spring Boot Developer，我们不需要担心所有这些事情。 Spring Boot Framework将为我们处理所有这些事情。

这就是Spring Boot Framework的魅力所在。

通过这种方式，Spring Boot框架避免了大量的样板代码和Spring配置，缩短了开发时间并提高了生产率。

这里我们没有讨论Spring Boot Annotations，Spring Boot API，Spring Boot应用程序中main(）方法的用法等等。我们将在使用Spring Boot IDE的帖子中回答所有这些问题。