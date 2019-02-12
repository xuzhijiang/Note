本模块介绍Spring Boot Web开发技术，主要包容两部分：

1. Spring Boot MVC应用
2. Spring Boot RESTful Service开发

本讲将会展示Spring Boot与Spring Boot MVC之间的区别

## Spring Boot MVC概述

### 技术全局视图

                  HTTP/HTTPS
Clients     <------------------->   Spring Boot App (REST API)

>Spring Boot MVC是实现上述移动互联网后端应用的主流开发框架之一.

### Java平台Web开发技术栈的转移

从过去的SSH(Spring Framework + Structs + Hibernate) 到
现在的SSM(Spring Boot + Spring Boot MVC + Mybatis)

### 学习Spring Boot MVC技术之前提

Spring Boot MVC是Spring技术家族中用于快速开发Web应用的一个框架，其中的MVC是Model-View-Controller的缩写

Spring Boot MVC = Spring框架 + Servlet + JSP

> 以上是Spring MVC的技术依赖关系（注：仅适用于Servlet技术栈）

### Spring Boot与Spring Boot MVC

1. 早在Spring Boot之前， Spring MVC就己经存在。
2. Spring Boot本质上不过是简化了原有Spring MVC应用的“启动（Boot）”和“配置（Config）”过程罢了，所以，“自动化”后的Spring MVC就被称为是“Spring Boot MVC”，不加Boot的，通常指传统的Spring MVC。
3. Spring Boot不是取代了早期的Spring MVC，而是对SpringFramework原有技术的改进和升级，与Spring MVC这种基于SpringFramework的上层应用框架不是一个层面上的东西。

### 了解一下Spring Boot MVC应用的分层架构图

开发Spring Boot MVC应用需要用到缓存、数据库存取、视图渲染等相关技术。

### Spring MVC是如何响应HTTP请求的？

要理解[Spring Boot MVC应用的分层架构图.png]，需要先掌握Java传统Web开发技术（Servlet），并且熟练掌握Spring Framework的基础知识

### 技术的当前进展

1. 可以使用传统的Java Servlet实现Spring MVC的所有功能（适用于Spring Boot 2.0以前的版本）
2. 从Spring Boot 2.0开始， Spring Web开发技术进行了扩展和演化，现在同时支持传统的Servlet技术栈和新增的Reactive技术栈，现在两者都可以用于开发当前流行的“前后端分离”的Web应用，另外，后者更适合于开发微服务.

### 初识Spring Boot MVC

> IntelliJ示例项目： spring_boot_web

#### 使用IntelliJ创建Spring Boot Web项目

1. Create New Project
2. 选择Spring Initializr创建
3. Spring Web项目Spring Boot Web项目习惯上多数使用Maven构建
4. 如果需要跑在外部的Tomcat容器中，这里需要选中“war”，否则，采用默认值“jar” ，这时， SpringBoot将启动一个内嵌的Tomcat容器运行Web项目。
5. 选择Web项目的可选功能模块(Template Engines)
6. 选择Spring boot版本
7. 开发Spring Web项目,Web依赖是必选的，如果要开发经典的MVC项目，视图引擎官方通常推荐选择Thymeleaf，如果要访问数据库，可以从SQL或NoSQL列表中选择相应
的依赖（比如MySQL或MongoDB）。
8. 己选择的Web功能模块列在这里，必要时可以移除
9. Finish之后， IntelliJ创建项目模板，并从网上下载Spring相关组件，第一次下载
需要较长的时间，请耐心等待其完成。

### IntelliJ创建之Spring Boot Web项目结构

1. src/main/java下的程序入口： SpringBootWebApplication(注意项目名称为:spring_boot_web)
2. src/main/resources下的配置文件：application.properties
3. src/test/下的测试入口：Chapter1ApplicationTests
3. Maven配置放在： pom.xml
4. Maven的资源文件目录： /src/Java/resources
5. spring-boot项目静态文件目录(比如css)：/src/java/resources/static
6. spring-boot项目thymeleaf模板文件目录：/src/java/resources/templates/

> 生成的SpringBootWebApplication.java和SpringBootWebApplicationTests.java类都可以直接运行来启动当前创建的项目.

### Spring Boot的Web自动配置特性

使用IntelliJ等IDE创建Spring Boot Web项目之后，会将相关依赖放到pom.xml中。

pom.xml中如果有thymeleaf和web依赖， Spring Boot会进行自动的配置，在程序运行时启动一个内嵌的Tomcat容器，默认情况下，会在8080端口监听，并且自动设置thymeleaf为视图引擎。

### 启动运行

已建立之`URL`与`控制器`方法之间的映射关系.

Tomcat在8080端口监听，如果需要调整这个
端口，可以在application.properties文件中加入相应配置参数进行修改

### 访问网站

access: localhost:8080

之所以出现上述的出错页面，是因为一个“空”的Spring Boot项目不知道如何响应的HTTP请求，所以显示了一个默认的出错页面。下面向项目中添加控制器以生成响应……

### MVC架构下各组成元素之间的合作关系

典型的MVC文件组织方式: 控制器 + 视图 -> 合成浏览器页面显示

### 如何部署Spring Boot Web项目？

Spring Boot项目主要有两种部署方式：

1. 打包为war，然后部署到Tomcat这种外部的Servlet容器中。
2. 构建一个“自包容”的jar包（称为Fat JAR），然后使用java -jar命令直接运行。 Fat JAR内嵌一个Tomcat容器，所以可以单独部署，独立运行。

> 新项目推荐使用第2种方式。

#### 使用命令行打包

在项目文件夹上运行以下命令： mvn clean package

1. 使用命令行打包的前提，是Maven的环境变量己经设置好。
2. 所谓“项目文件夹”，指的是pom.xml所在的那个文件夹
3. 会看到在target生成了jar包，其名字就是在pom.xml中指定的artifactId + 版本号，另一个以“original”结尾的是不包容依赖包的原始打包结果，不能独立运行。

#### 创建Fat JAR并运行

使用以下命令即可运行之：java -jar生成的jar包文件名.jar

access: localhost:8080/hello

### 在IntelliJ中直接打包部署

1. 在IntelliJ右部可以打开Maven面板，里面可以找到Maven的相应命令，比如package,鼠标双击直接运行之……
2. 打包完成之后，在IntelliJ的Project视图中可以发现一个target文件夹，里面就有打包生成的jar文件。
3. 部署到Tomcat:将Spring Boot Web项目部署到外部Servlet容器（比如Tomcat）中(war的形式)

### 小结

1. 本讲以IntelliJ为工具，介绍了一个典型的SpringBoot MVC项目的HelloWorld流程。
2. 请在文档的指导下，自己将整个流程走一遍，对SpringBoot MVC形成一个感性认识，为后继学习打下基础。

### Spring Boot的Tomcat部署

> 项目: spring_mvc_demo

#### 概述

1. 默认情况下， Spring Boot Web项目会编译成jar，使用内嵌的Tomcat运行，这对于开发单个的Web应用是很方便的，尤其是在开发微服务的场景下。
2. 但可能仍然存在着这种需求，那就是早期的Servlet/JSP项目与新开发的Spring Boot Web项目需要部署到同一台服务器上，所以，只安装一个Tomcat实例，让它来管理部署到本机的所有Web项目，可能还是一种现实的选择。
3. 幸运的是，Spring Boot的设计者己经考虑到了这种情况，只需做简单的设置，用几步就可以很方便地为Spring Boot Web项目生成war包，部署到外部独立的Tomcat上。本讲介绍具体步骤。

#### 部署到Tomcat容器

1. 步骤一、修改打包方式打开pom.xml，将打包方式由jar改为war
2. 步骤二、调整嵌入式Tomcat插件的编译方式,默认情况下， spring-boot-starter-web会启动一个嵌入式的tomcat，因为现在我们是要生成一个war包，跑在外部的tomcat上，
所以，给项目添加一个tomcat依赖(spring-boot-starter-tomcat)，并将其scope设置为“provided（表明这些组件由外部容器提供）”从而覆盖掉默认设置。
3. 步骤三、修改启动类，并重写初始化方法(SpringMvcDemoApplication.java)
4. 方法步骤四、使用Maven打包,打开IntelliJ的Maven面板，运行Maven的package命令……,复制文件,在IntelliJ项目的target文件夹下，可以找到war包，将其复制到tomcat的webapps文件夹下，为了方便，将文件名改为myweb.war启动Tomcat……访问localhost:8080(localhost:8080/myweb/index.html)
5. 注意一下静态资源的路径问题。开发时使用的是localhost:8080，部署
之后，其根路径为myweb，需要在网页中正确地设置静态资源的路径，否则会找不到相应的文件。Thymeleaf的方法是使用th:href来生成链接，详情请看示例文件。

### 小结

1. 本讲以一个典型的Spring Boot MVC项目为例，介绍了如何在IntelliJ中将项目打包为war，然后部署到外部Tomcat的基本过程。
2. 在这个过程中，注意一下静态资源的路径问题，它可能会带来不小的麻烦。