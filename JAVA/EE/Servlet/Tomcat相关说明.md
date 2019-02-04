### Tomcat

在最新的Spring Boot项目中，Tomcat己经被嵌入到了Spring Boot项目中，无需单独安装，而Servlet（甚至包括JSP），都仍然在Spring Boot项目中可用。

Tomcat是Java平台著名的一个Servlet容器（也是一个
Web Server）， 在实际开发中得到了广泛的应用。
Tomcat、 Jetty是一个Servlet容器，但不是Java EE容器.

#### Tomcat文件夹说明

1. conf: 里面包容Tomcat的配置文件，可以人工编辑修改它
2. lib: 包容诸多的jar包，开发Web项目需要引用它
3. webapps: 写好的网站代码和资源放在这里， Java Web网站通常会被打包为.war文件， Tomcat在运行时会动态地发现并解压它。
4. bin: 存放启动和关闭Tomcat命令的路径
5. logs: 保存日志文件

#### 修改Tomcat监听的端口

默认情况下， Tomcat监听的是8080端口，如果希望监听其他端口，
可以在conf文件夹下的Server.xml内查找以下内容并改之：

```xml
<Connector port="8080" protocol="HTTP/1.1"
connectionTimeout="20000"
redirectPort="8443" />
```

#### Tomcat能运行的Web应用文件组织结构

```
.
└── WEB-INF
    ├── classes
    │   └── cc
    │       └── openhome
    │           └── HelloServlet.class(放置类文件，含包的层级)
    ├── lib
    │   └── xxx.jar(防止JAR文件)
    └── web.xml(部署描述符)
```

#### Tomcat部署war文件

```xml
<Host name="localhost"  appBase="webapps"
            unpackWARs="true" autoDeploy="true">
        <!-- 这个配置的意思是把webapps/test文件夹下的内容暴露到
            path="/"下，这样我们直接访问localhost:8080/就可以访问test下的内容了,
            而且访问相对于test路径的内容也没问题-->
        <Context path="/" docBase="test" debug="0" privileged="true"/>
      </Host>
```

#### 关于Tomcat的学习策略

1. Tomcat在`传统的Java Web`开发项目中有很重要的地位，有大量的Web网站部署在Tomcat之上.
2. 必须指出，在现代互联网应用中Tomcat的地位在下降，一是现在普遍采用`前后端分离`的
Web应用架构，`全部由静态资源`组成的Web前端通常部署在`Nginx等对静态资源更友好`的
Server而不是Tomcat上，二是Web Server日益退化为`仅提供数据服务而不是生成HTML`的角色，
而且现在`微服务架构`流行，微服务放松了对技术平台的限制，可选的技术很多，比如Node.js，
ASP.NET core等都可以取代Tomcat。