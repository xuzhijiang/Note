Spring Boot Initilizr用于快速启动新的Spring Boot Maven/Gradle项目。它生成初始项目结构并构建脚本以减少开发时间。

Spring Boot Initilizr有以下几种形式：

1. Spring Boot Initilizr With Web Interface
2. Spring Boot Initilizr With IDEs/IDE Plugins
3. Spring Boot Initilizr With Spring Boot CLI
4. Spring Boot Initilizr With ThirdParty Tools

现在我们将讨论如何使用“Spring Boot Initilizr with Spring Boot CLI”选项来引导Spring应用程序

### Spring Boot Initilizr With Spring Boot CLI

Spring Boot CLI提供了一个“spring init”命令来引导Spring Applications。

我从“https://start.spring.io”下载了Spring Boot CLI zip，并在“D：\ spring-boot-cli-1.2.3.RELEASE”中安装了该软件。

设置以下系统变量：

`PATH=D:\spring-boot-cli-1.2.3.RELEASE\bin;%PATH%`

#### “spring init” command

“spring init”命令是Spring Boot CLI Component中易于使用的命令。它使用“https://start.spring.io”托管的“Spring Initilizr服务”（默认情况下，我们也可以指定目标URL。我们将在下一节讨论这个。）使用Spring Boot CLI组件来引导Spring或Spring Boot应用程序。

#### “spring init” syntax

spring init [options] [location]

这里“options”是命令选项，“location”指定创建新的Spring Boot Project文件系统位置。

使用“spring help init”命令查看所有可用的“spring init”命令选项

#### The “spring init” command examples

现在我们将探索“spring init”命令选项。打开CMD(命令提示符):

示例1： - 使用默认设置创建Spring Boot项目：

> spring init

它在当前工作目录下使用默认设置创建新的Spring Boot Project zip文件为“demo.zip”.

注意： - “spring init”默认设置：

a. 默认构建工具是“maven”。
b. 默认的Spring Initilizr服务目标URL：https：//start.spring.io
c. 默认项目名称：“demo”
d. 默认maven类型：“jar”
e. 添加到构建脚本文件的默认Spring Boot依赖项：

	```
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

f. Default Maven artifacts:

	```
	 <groupId>org.test</groupId>
	    <artifactId>demo</artifactId>
	    <version>0.0.1-SNAPSHOT</version>
	    <packaging>jar</packaging>
	    <name>demo</name>
	```

示例2： - 使用所需的依赖项和项目名称创建Spring Boot项目

> spring init -d=web,data-jpa,jms,ws SpringMVCMavenToolProject.zip

它使用JPA，JMS和WS功能创建了一个打包的Spring Boot WebApplication。

	a. 这里“-d”选项意味着我们可以用逗号分隔符列出所有必需的功能.
	b. SpringMVCMavenToolProject.zip是在当前工作目录中使用该名称创建的项目名称和zip文件.

示例3： - 为Gradle Build Tool创建具有所需依赖项和项目名称的Spring Boot项目。

spring init -d=web,data-jpa,jms,ws --build=gradle -p=war SpringMVCGradleToolProject

它为Gradle构建工具创建了一个解压缩的Spring Boot WebApplication，其中包含JPA，JMS和WS功能。

	a. “-d”选项意味着我们可以用逗号分隔符列出所有必需的功能
	b. “-build”选项指定必需的构建工具。它接受两个值：maven（默认）和gradle。
	c. “SpringMVCGradleToolProject”是在当前工作目录中创建的项目名称。
	d. “-p”或“-packaging”选项指定包装类型。默认值为“jar”。

示例4： - 为Maven Build Tool创建具有所需依赖项和项目名称的Spring Boot项目。

spring init -d=web,jdbc,ws,cloud-aws,h2 --build=maven --packaging=war SpringMVCMavenToolProject.zip

它为Maven构建工具创建了一个带有JDBC，AWS Cloud和WS功能的打包Spring Boot WebApplication。

	a. “-d”选项意味着我们可以用逗号分隔的值列出所有必需的功能。
	b. “-build”选项指定必需的构建工具。它接受两个值：maven（默认）和gradle。
	c. “SpringMVCMavenToolProject”是在当前工作目录中创建的项目名称。
	d. “-p”或“-packaging”选项指定包装类型。默认值为“jar”。它接受“pom，jar，war，ear，rar，par”

示例-5： - 使用指定的Spring Boot和Java版本为Maven Build Tool创建具有所需依赖项和项目名称的Spring Boot项目

默认情况下，“spring init”命令将采用“System Variables”并采用适当的Spring Boot和Java版本。但是，可以指定Spring Boot和Java Versions。

我的Windows系统配置了Java Version = 1.8和Spring Boot version = 1.2.3.RELEASE。但是我想在创建新的Spring Boot项目时更改它们:

spring init -d=web,jdbc,ws,cloud-aws,h2 --build=maven --java-version=1.7 --boot-version=1.2.5.RELEASE -packaging=war SpringMVCMavenToolProject.zip

它使用Java 1.7和Spring Boot 1.2.5.RELEASE为Maven构建工具创建了一个带有JDBC，AWS Cloud和WS功能的打包Spring Boot WebApplication。

	a. “-j”或“-java-version”选项用于指定Java版本，如1.7,1.8等。
	b. “-b”或“-boot-version”选项用于指定Spring Boot Framework版本，如1.2.5.RELEASE，1.3.0.M1等
	c. “-d”选项意味着我们可以用逗号分隔符列出所有必需的功能。
	d. “-build”选项指定必需的构建工具。它接受两个值：maven（默认）和gradle。
	e. “SpringMVCMavenToolProject”是在当前工作目录中创建的项目名称。
	f. “-p”或“-packaging”选项指定包装类型。默认值为“jar”。它接受“pom，jar，war，ear，rar，par”

注意：-
当我们执行“spring init”命令时，我们可以观察到以下消息，说它将连接“Spring Boot Initilizr默认服务”: Using service at https://start.spring.io

同样，我们可以使用其他“spring init”选项来探索它们。例如，“ -  force”强制更新已存在的相同项目名称以及更新以避免错误。

注意：-

我们可以提取使用“spring init”命令创建的zip文件，并将这些项目导入我们最喜欢的Spring IDE（例如： -  Spring STS Suite），并继续开发我们的项目相关需求。

以下部分列出了受支持的构建选项:

+--------+----------------------------------------------------------------------+
| Id     | Description                                                          |
+--------+----------------------------------------------------------------------+
| maven  | Creates a Maven project with pom.xml file for Maven build tool       |
| gradle | Creates a Maven project with build.gradle file for Gradle build tool |
+-------------------------------------------------------------------------------+

默认构建参数值为“maven”

以下部分列出了以逗号分隔的“依赖项”列表支持的标识符。

从这个列表中，我们已经在我们的示例中使用了“web，data-jpa，jms，ws”。 还请尝试使用其他功能或依赖项来开发一些示例。 
