## 创建Spring Boot项目的方法

>http://start.spring.io

### 使用IntelliJ创建Spring 

Create -> New Project -> Spring Initializr, 选择Default: https://start.spring.io注意保证网络通畅！) -> next -> Project Metadata(名字要使用全小写字母，以遵循maven规范), -> next -> 在这里可以指定要使用的
Spring Boot版本, 
在本示例程序中，选中“Web”选项，我们将开发一个Web应用程序。


还可以使用Maven在控制台启动。方法是进入到项目文件夹，然后执行：
`mvn spring-boot:run`, 本质上就是使用java –jar执行项目生成的jar包

### 部署和测试这个应用程序

1. 拷贝我们的war文件“SpringMVCExample-1.0.0-BUILD-SNAPSHOT.war” 
到${TOMCAT8_HOME}/webapps/文件夹下

2. 执行D:\apache-tomcat-8.0.24\bin>startup.bat这个脚本，打开Tomcat服务器

3. 测试我们的应用程序，用http://localhost:8080/SpringMVCExample-1.0.0-BUILD-SNAPSHOT/
访问主页.用localhost:8080/SpringMVCExample-1.0.0-BUILD-SNAPSHOT/login/访问登录页,
提供Username，然后点击"Login"按钮，然后观察会展示的下一个页面.

