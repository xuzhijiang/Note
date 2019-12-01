# Spring Boot的嵌入式容器

内嵌web容器的两个默认配置项：默认的监听端口:8080,应用的contextPath为"/"。如我们想要覆盖默认的配置，可以按照如下方式进行：

```yaml
#Tomcat configuration
server.port=80
server.contextPath=/springboot
```

![](pics/默认使用tomcat容器.png)

![](pics/自动包含tomcat依赖.png)

# 如何部署Spring Boot Web项目

Spring Boot项目主要有两种部署方式：

1. 打包为war，然后部署到Tomcat这种外部的Servlet容器中。
2. SpringBoot通过`spring-boot-maven-plugin`构建了一个“自包容”的jar包(称为Fat JAR），然后使用java -jar命令直接运行。通常我们将一个可以运行的 jar包称之为fat jar 。因为这样的 jar包内部通常包含了自己运行时的所有依赖，体积比较大

springbootstudy-0.0.1-SNAPSHOT.jar是我们打包好的，内部包含了其他依赖的，可以直接运行的jar，而springbootstudy-0.0.1-SNAPSHOT.jar.original 则是原始的打包后的jar。进入target生成文件夹所在目录，我们可以看看这两个文件的区别：第一个可以直接运行的springbootstudy-0.0.1-SNAPSHOT.jar的大小为13M 左右，而第二个只有 6KB。可以直接解压这个 jar文件，就会发现这个jar的 lib目录下，实际上存放了所有依赖的 jar。而 springbootstudy-0.0.1-SNAPSHOT.jar.original 则是没有包含这些依赖的 jar的原始包

我们可以通过命令`java -jar springbootstudy-0.0.1-SNAPSHOT.jar`来直接运行这个jar.

# 将项目打包为war，然后部署到外部Tomcat容器

1. 步骤一、修改打包方式打开pom.xml，将打包方式由jar改为war
2. 步骤二、调整嵌入式Tomcat插件的编译方式,默认情况下， spring-boot-starter-web会启动一个嵌入式的tomcat，因为现在我们是要生成一个war包，跑在外部的tomcat上，所以，给项目添加一个tomcat依赖(spring-boot-starter-tomcat)，并将其scope设置为“provided(表明这些组件由外部容器提供）”从而覆盖掉默认设置。
