## Spring Boot Initilizr With ThirdParty Tools

我们还可以使用以下第三方工具来引导Spring Boot应用程序：

1. CURL Tool
2. HTTPie Tool

### Spring Boot Example with “curl” Tool

“curl”是一个开源库和命令行工具，用于使用各种协议(如FTP，FTPS，HTTP，HTTPS，SMTP，POP3，LDAP等）传输数据。我们也可以使用此工具轻松地引导Spring Boot应用程序。

“curl”官方网站可在以下网址获得：http：//curl.haxx.se/

“curl”提供了两个项目：

1. cURL Project – command line tool
2. libcurl Project- library

“cURL” Tool setup

请访问“http://curl.haxx.se/download.html”网址以下载“cURL”工具。

springboot卷曲下载

我已经下载了Windows 64Bit zip文件

在Windows中，我们需要设置此系统变量：

> PATH=D:\SpringBoot;%PATH%

“cURL” Syntax:

我们需要按照以下语法来创建Spring Boot应用程序。

> curl [options ...] <url>

我们可以通过执行“curl -help”命令获得所有可用选项。引导Spring引导应用程序是Spring Initializr URL : https://start.spring.io。

执行“curl start.spring.io”时，我们可以看到所有可用的参数，Spring Boot列表“依赖关系”或“功能”等。

“cURL”示例：

示例-1： - 创建默认Spring Boot项目

curl http://start.spring.io/starter.zip -o SpringBootCurlDefaultProject.zip

它在当前工作目录中为Maven构建工具创建一个名为“SpringBootCurlDefaultProject.zip”的Default Spring Boot项目

示例2： - 为Gradle构建工具创建具有所需“依赖关系”和所需打包类型的Spring Boot项目

curl http://start.spring.io/starter.zip -d dependencies=web,data-jpa,jms,ws
     -d packaging=war 
     -d type=gradle-project
     -o SpringBootCurlWebProject.zip

这里我们使用“-d”选项指定参数。

“-d type=gradle-project”表示创建Gradle项目。我们可以为Maven项目指定相同的方式：“-d type=maven-project”(它是“type”参数的默认值）。

示例3： - 为Maven构建工具创建具有所需“依赖关系”和所需打包类型的Spring Boot项目


curl http://start.spring.io/starter.zip 
     -d dependencies=web,jdbc,ws,cloud-aws,h2 
     -d packaging=war 
     -d type=maven-project
     -o SpringBootCurlWebMavenProject.zip

这里我们使用“-d”选项指定参数。

“-d type=gradle-project”表示创建Gradle项目。 “type=maven-project”使用pom.xml文件创建Maven项目(它是“type”参数的默认值）。

它使用Spring MVC，Spring JDBC，Spring WS，带有AWS的Spring Cloud，H2数据库功能创建Mavenized项目。

示例4： - 通过指定Java和Spring Boot Framework版本，为Gradle构建工具创建具有所需“依赖关系”和所需打包类型的Spring Boot项目。

默认情况下，“spring init”命令将采用“系统变量”并采用适当的Spring Boot和Java版本。但是，可以指定Spring Boot和Java Versions。

我的Windows系统配置了Java Version = 1.8和Spring Boot version = 1.2.3.RELEASE。但是我想在创建新的Spring Boot项目时更改它们，如下所示。

curl http://start.spring.io/starter.zip 
     -d dependencies=web,jdbc,ws,cloud-aws,h2 
     -d packaging=war 
     -d type=gradle-project
     -d javaVersion=1.7
     -d bootVersion=1.2.5.RELEASE
     -o SpringBootCurlWebGradleProject.zip

这里我们使用“-d”选项指定参数。

	“-d javaVersion=1.7”选项用于指定Java版本，如1.7,1.8等。
	“-d bootVersion=1.2.5.RELEASE”选项用于指定Spring Boot Framework版本，如1.2.5,1.3等。
	“-d type=gradle-project”表示创建Gradle项目。
	“type = maven-project”使用pom.xml文件创建Maven项目(它是“type”参数的默认值）。
	“-d dependencies=web，jdbc，ws，cloud-aws，h2”指定了我们的项目功能。
	“-d packaging=war”选项指定包装类型为“war”。默认值为“jar”。它接受“pom，jar，war，ear，rar，par”

它使用Spring MVC，Spring JDBC，Spring WS，Spring Cloud with AWS，H2 Database功能创建Mavenized项目，并使用Java Varsion=1.7和Spring Boot Framework版本=1.2.5.RELEASE。


这是关于“curl”工具用简单的命令引导Spring Boot应用程序。我们可以将这些项目导入Spring IDE，并根据我们的项目要求进行增强。

注意：-

我们可以参考“Spring Boot Initilizr with Spring Boot CLI”中的“依赖关系”的逗号分隔列表，知道支持的标识符列表。

以下部分列出了受支持的构建选项：

+-----------------+-----------------------------------------+
| Rel             | Description                             |
+-----------------+-----------------------------------------+
| gradle-build    | Generate a Gradle build file            |
| gradle-project  | Generate a Gradle based project archive |
| maven-build     | Generate a Maven pom.xml                |
| maven-project * | Generate a Maven based project archive  |
+-----------------+-----------------------------------------+

默认构建参数值为“maven-project”

URI模板采用一组参数来自定义对链接资源的请求结果

+-----------------+------------------------------------------+------------------------------+
| Parameter       | Description                              | Default value                |
+-----------------+------------------------------------------+------------------------------+
| applicationName | application name                         | DemoApplication              |
| artifactId      | project coordinates (infer archive name) | demo                         |
| baseDir         | base directory to create in the archive  | no base dir                  |
| bootVersion     | spring boot version                      | 1.2.5.RELEASE                |
| dependencies    | dependency identifiers (comma-separated) | none                         |
| description     | project description                      | Demo project for Spring Boot |
| groupId         | project coordinates                      | org.test                     |
| javaVersion     | language level                           | 1.8                          |
| language        | programming language                     | java                         |
| name            | project name (infer application name)    | demo                         |
| packageName     | root package                             | demo                         |
| packaging       | project packaging                        | jar                          |
| type            | project type                             | maven-project                |
| version         | project version                          | 0.0.1-SNAPSHOT               |
+-----------------+------------------------------------------+------------------------------+

### Spring Boot Example with “HTTPie” Tool

使用“HTTPie”工具的Spring Boot示例
与CURL工具一样，HTTPie是一个命令行HTTP客户端，可以使用简单的命令来引导Spring Boot应用程序。它是一个人性化的基于JSON的工具。它主要用于测试，调试和与HTTP Web服务或Web服务器交互。

Httpie官方网站：https：//github.com/jkbrzt/httpie

“HTTPie” Tool Setup

1. 从“https://www.python.org/downloads/”下载最新的Python Windows Installer并安装它。
2. 我已经安装了Python 2.7，使用zip文件安装非常简单。如果您使用Windows Installer，则只需按照默认设置进行安装即可。
3. 从“https://pip.pypa.io/en/latest/installing.html#install-pip”下载“get-pip.py”
只需鼠标右键单击并保存为您所需的文件系统路径：
4. 使用以下命令安装“pip”

> python get-pip.py

5. 使用以下命令安装“httpie”工具

> python -m pip install --upgrade httpie

#### “HTTPie”的例子

示例-1： - 创建默认Spring Boot项目

> python -m httpie https://start.spring.io/starter.zip -d

它创建了一个新的Spring Boot项目，默认设置如下：create project filename是“demo.zip”，Build工具是“Maven”。它采用System Variables的默认Java版本和Spring Boot Framework版本。

示例2： - 创建Spring Boot WebAppilcation(war）项目具有Gradle构建工具所需的依赖项


python -m httpie https://start.spring.io/starter.zip 
     dependencies==web,data-jpa,jms,ws
     type==gradle-project
     packaging==war
     -d

它使用Spring MVC，Spring DATA JPA，Spring JMS，Spring WS Capabilities创建了一个Gradle项目。

注意： - 在这里我们应该观察到我们需要在参数或选项名称和值之间使用双等号，如“==”。

我们可以参考“Curl工具”部分来了解支持的“依赖项”，“参数”和构建工具选项的列表。 HTTPIE和Curl工具共享相同的选项和参数。

这就是“使用Thard Party Tools进行Spring Boot Initilizr”的全部内容。
