maven是构建和依赖管理工具

中央服务器地址(central server):https://repo.maven.apache.org/maven2/

一个jar包的maven如何配置：https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5

查找jar包的顺序:

1. find jar: local repo(本地仓库)
2. private service repo(Enterprise企业私有仓库)
3. central repo(中央仓库)

![maven central repo all plugins(maven中央仓库的所有插件地址:)](https://repo.maven.apache.org/maven2/org/apache/maven/plugins/)

![find pom.xml dependency jar format](http://mvnrepository.com/)

MAVEN的元素:
1. <groupId>
2. <artifactId>
3. <version>


1. 查看maven版本: mvn -version

2. mvn package(创建JAR/WAR/EAR---要将我们的应用程序部署到任何Web或应用程序服务器,
我们需要使用以下maven命令创建应用程序JAR/WAR/EAR文件)

3. mvn test(要在不创建应用程序JAR/WAR/EAR文件的情况下仅运行JUnit测试)
4. mvn clean(总是建议在编译和构建我们的应用程序代码之前清理所有内容--删除构建目录)
5. mvn –help
6. mvn install(要编译，构建(构建成jar/war/ear文件)并且安装到本地maven存储库)
7. mvn –version
8. mvn install -DskipTests 或 mvn install -Dmaven.test.skip=true(要跳过JUnit测试并执行其他任务)
9. mvn test package (运行JUnits并创建JAR/WAR/EAR[To compile, tests and assemble去编译，测试，然后组装成jar/war/ear文件]---注意：在maven和gradle中，我们可以管道两个或更多命令执行它们。)
10. mvn deploy(将应用程序WAR/EAR文件部署到服务器)
11. mvn jetty:run(要在Jetty嵌入式服务器上运行---run on Jetty embedded server)
12. mvn jetty:run-war(构建WAR文件，并且部署并运行它到嵌入式jetty服务器中)
13. mvn jar(从已经编译好的class文件创建JAR文件)
14. mvn eclipse:eclipse(生成项目和所有Eclipse所需的文件)
15. mvn eclipse:clean(清理Eclipse所需的所有文件)
16. mvn help:describe -Dplugin=eclipse(查看eclipse插件的所有命令)
17. Goal Prefix:eclipse(目标前缀是eclipse)


mvn compile (just compile)
mvn test (compile and run unit tests)
mvn package (compile, run unit tests, and build the distributable package)
mvn install (all of the above, and install distributable package into local repository.Install is very useful if you need to build other packages which depend on changes to this package)
mvn deploy (all of the above, and install package into remote (aka public) repository for sharing with other developers)

### Maven配置文件Scope解释

>scope有compile、test、runtime、provided、system，其中默认的值是compile

1. Compile:缺省值，适用于所有阶段(编译，测试，运行)，会随着项目一起发布，是一个比较强的依赖。打包的时候需要包含进去。
2. Test: 表示依赖项目仅仅参与测试相关的工作，只在测试时使用，用于编译和运行测试代码。不会随项目发布。比较典型的如junit,junit只有在执行单元测试时候需要，当我们进行真正项目发布的时候junit是不需要进行编译和发布的。
3. runtime: 被依赖项目无需参与项目的编译，适用运行和测试阶段。与compile相比，跳过编译而已，举例说明一下：在代码中调用了一个接口一个方法，这个接口并没有对应的实现。这段代码在编译期间并不会报错，但是在代码运行的时候会出现问题。jdbc驱动可以使用runtime的scope，因为只有在真正运行的时候才会调用到驱动的代码。
4. provided: 打包的时候不打包进去,别的设施(例如web容器)会提供。该依赖参与编译，测试，运行等周期。相当于compile，但是在打包阶段做了exclude的动作

```xml
<!-- tomcat会提供这个servlet-api.jar 包，所以当我们项目发布的时候这个包是不需要打到包里的 -->
<dependency>  
 <groupId>javax.servlet</groupId>  
  <artifactId>servlet-api</artifactId>  
    <version>2.5</version>  
    <scope>provided</scope>  
</dependency>  
<dependency>  
    <groupId>javax.servlet.jsp</groupId>  
    <artifactId>jsp-api</artifactId>  
    <version>2.1</version>  
    <scope>provided</scope>  
</dependency>  
```

5. 从参与度(编译，测试，运行)来说，也provided相同，不过被依赖项不会从maven仓库抓，而是从本地文件系统拿，一定需要配合systemPath属性使用。