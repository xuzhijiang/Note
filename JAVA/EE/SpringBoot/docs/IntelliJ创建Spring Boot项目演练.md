## 两种创建Spring Boot项目的方法

>Web Initializr: http://start.spring.io

可以直接访问Spring网站在线创建好项目再下载，也可以在IntelliJ、STS等IDE中“间接”地调用Spring官网的这一功能创建项目.

>使用命令行: Spring Boot CLI

CLI(command line interface）是一种非常常见的项目创建方式，有时，它被称为“脚手架”，在Web前端项目(比如Angular、 Vue和React）中大量应用。

不管是使用命令行还是IDE， 本质上都是通过一个名为Spring Initializr的Web应
用来构建一个Spring Boot项目框架的。

Initializr创建的项目，提供了构建SpringBoot应用程序所需的基本架构，我们可
以在此基础上添加自己的文件。

### 使用IntelliJ创建Sping 

Create -> New Project -> Spring Initializr, 选择Default: https://start.spring.io注意保证网络通畅！) -> next -> Project Metadata(名字要使用全小写字母，以遵循maven规范), -> next -> 在这里可以指定要使用的
Spring Boot版本, 在本示例程序中，选中“Web”选项，我们将开发一个Web应用程序。->next -> 指定完项目路径之后，IntelliJ就开始下载模板，请耐心等待……下载完毕之后， IntelliJ会打开项目，并在右下角弹出一个消息框： -> 点击”Import Changes”，导入
Maven依赖。第一次导入时会花费较长的时间，以后就快了。

Spring Boot其实是一个简单的Java Application！

Run -> Edit Configure -> 选择SpringBoot后看到Main Class.所有一切，IntelliJ都会帮助我们创建好，不需要我们手工进行配置。

#### 查看pom.xml:

```
Spring Framework相关的jar包， 被这个”父”starter(spring-boot-starter-parent)依赖打包在一起，无须单独声明。

这个starter(spring-boot-starter-web)依赖包容Web相关的jar包声明

Maven插件负责构建SpringBoot项目: spring-boot-maven-plugin
```

#### IntelliJ自动生成的项目(配套demo：first_springboot_app）：

选择FirstSpringBootAppApplication -> 右击 -> 选择Run FirstSpringBootAppApplication， 可以看到， Spring Boot实际上会自动启动一个
Tomcat，并监听8080端口……

Spring Boot App启动之后，访问localhost:8080，默认会显示一个Error页面。
我们需要创建一个控制器，并生成一些可显示的内容给浏览器显示。

创建一个controllers包，通常情况下，这个包用于集中保存Web控制器类
分别创建两个控制器，一个是REST的，一个是MVC的……

测试结果：

1. localhost:8080/spring/hello(访问REST控制器,访问Web控制器返回“纯”文本，多用于“前
后分离的Web应用。或者是在移动互联系统中为手机App提供数据服务)
2. localhost:8080/springWeb/hello(返回HTML文本，主要供浏览器显示)

还可以使用Maven在控制台启动。方法是进入到项目文件夹，然后执行：
`mvn spring-boot:run`, 本质上就是使用java –jar执行项目生成的jar包

>注意：上述命令执行成功的前提是Maven己经提前装好并设置了Path变量。

### 小结

本讲介绍了使用IntelliJ创建Spring Boot Web项目的HelloWorld流程。

虽然本讲是以Spring Boot Web为例介绍的，但初学者不要马上去碰
这一块，而应该从Spring Framework起步开始学，学完之后，再学
Spring Web开发技术

如果使用IntelliJ导入Maven项目出现找不到相关Jar，则File -> Settings -> Maven -> Update, 而且IntelliJ还要启动auto import自动导入功能.

### 部署和测试这个应用程序

1. 拷贝我们的war文件“SpringMVCExample-1.0.0-BUILD-SNAPSHOT.war” 
到${TOMCAT8_HOME}/webapps/文件夹下

2. 执行D:\apache-tomcat-8.0.24\bin>startup.bat这个脚本，打开Tomcat服务器

3. 测试我们的应用程序，用http://localhost:8080/SpringMVCExample-1.0.0-BUILD-SNAPSHOT/
访问主页.用localhost:8080/SpringMVCExample-1.0.0-BUILD-SNAPSHOT/login/访问登录页,
提供Username，然后点击"Login"按钮，然后观察会展示的下一个页面.

