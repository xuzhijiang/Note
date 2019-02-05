使用jetty-maven-plugin进行测试

jetty-maven-plugin能够帮助我们节省时间，它会周期性地检查项目内容，发现项目变更后(包括src中webapp中内容)自动更新到内置的jetty Web容器中。然后就可以直接测试web页面了。
注意：jetty-maven-plugin主要通过将项目自动部署到jetty中从而方便测试，所以在生产环境中不应该使用该方式，因为生成环境中我们的项目有可能部署到其它web容器中tomcat、jboss,jetty等，所以它主要用来帮助日常快速开发和测试

pom.xml配置

```xml
<plugin>
   <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-maven-plugin</artifactId>
    <version>9.3.14.v20161028</version>
    <configuration>
      <scanIntervalSeconds>10</scanIntervalSeconds>
      <webApp>
        <contextPath>/test</contextPath>
      </webApp>
      <httpConnector>
        <port>8168</port>
        <host>localhost</host>
      </httpConnector>
    </configuration>
</plugin>
```

scanIntervalSeconds 表示该插件扫描项目变更的时间间隔，如配置默认0，表示不扫描。
contextPath：表示项目部署后的context path。可以通过http://hostname:port/test访问该应用。
port端口,默认8080
host配置主机名，默认localhost

现在运行如下命令启动jetty-maven-plugin：
mvn jetty:run

默认端口8080,希望使用其他端口通过jetty.http.port参数，如
mvn jetty:run -Djetty.http.port=9999

启动jetty之后，用户修改类、jsp、html等都会被插件扫描到并重新自动部署。
