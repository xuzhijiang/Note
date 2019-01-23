### JSP和Servlet如何学习?

JSP与Servlet都是早期Java Web开发的核心技术，拥有悠久的历史。虽然现在的Web项目很少直接使用它们来进行开发，但新技术是从老技术上“长”出来的，并不是全盘抛弃了老技术。

#### Servlet

比如,虽然Servlet已经被Spring MVC这样的框架所封装，但掌握它仍然是必需的. 再举例，Servlet就算是到了2018年才发布的Spring Boot 2.0中，它仍然是其核心技术栈之一。

#### JSP

与Servlet现在仍然是Java Web开发的基础不太一样，JSP现在己经用得越来越少了，但如果不学习它，后面的很多内容没法学习。所以，掌握JSP的基础知识，能看懂JSP代码，并且具备动手编写一些简单JSP页面的技能，仍然是需要的。

所以，我们还是应该学习与掌握Servlet与JSP的基础知识，只需注意一下“度”，并没有太多必要花费太多时间去系统掌握其各种技术细节，而应该把精力放在理解Servlet的工作原理，以及Servlet与像Tomcat这样的Servlet容器之间的关系即可。

### Servlet

Servlet和JSP只是Java EE众多技术中的两个，其他Java EE技
术还有Java消息服务(JMS)，企业Java对象(EJB)、 JavaServer Faces以及Java持久化等

### JSP、Servlet信息交换方式

四种Java Web状态保持的技术：

* URL重写
* 隐藏域
* Cookies
* HTTP Session

>这四种技术在现代Web开发中非常常用，尤其是后两个，使用得更多。
因此，学习与掌握这四种技术，知道其特性，懂得其原理是非常重要的。

### JSP与Servlet高级特性

介绍Java Web开发中的三个技术特性：

* 监听器
* 过滤器
* 异步处理

这三个特性在现代Web后端开发框架中得到了普遍的支持。特别是异步处理，更是发展态势良好，各Web后端开发框架都纷纷在这块推出新技术。

本讲所介绍的这三个技术特性是针对Servlet来说的，请注意它们与最新的Spring Boot 2.0所引入的技术之间是有差异的，但基本思想和原理则没有太多的变化。

另外，本讲还介绍了一个比较综合的实例——用于网站登录的图形验证码。

这个实例综合应用了本课程所介绍的大多数Servlet和JSP技术，能把它搞懂，就可以说你对Servlet和JSP技术的学习是有成效的，可以进入下一模块的学习了。

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

#### 关于Tomcat的学习策略

1. Tomcat在`传统的Java Web`开发项目中有很重要的地位，有大量的Web网站部署在Tomcat之上.
2. 必须指出，在现代互联网应用中Tomcat的地位在下降，一是现在普遍采用`前后端分离`的
Web应用架构，`全部由静态资源`组成的Web前端通常部署在`Nginx等对静态资源更友好`的
Server而不是Tomcat上，二是Web Server日益退化为`仅提供数据服务而不是生成HTML`的角色，
而且现在`微服务架构`流行，微服务放松了对技术平台的限制，可选的技术很多，比如Node.js，
ASP.NET core等都可以取代Tomcat。

### IntelliJ开发Web项目

IntelliJ IDEA是最受欢迎的Java集成开发环境，在实际开
发中被广泛地使用。

创建一个全局的库以便：

> File -> Project Structure -> Global Libraries -> + -> Java
-> 选择Tomcat安装文件夹下的lib子文件夹，它包
容Java Web项目所需的各种jar包 -> ok -> 给lib取名tomcat-8.5

设置Web项目模板依赖：

> File -> Project Structure -> Modules -> Dependencies
，使项目依赖于tomcat-8.5的库，并且其Scope是Provided,表明
这些包是由外部容器提供的。

在完成了编译相关的设置之后，我们接着需要设置“Run”相关的配置:

> Run -> Edit Configurations...

在Run/Debug Configurations窗口中，默认情况下左边列表是空的（除
非你以前创建过相应的配置），点击左上角“+”号，选择“Tomcat Server”，创建一个新的“Local(本地)” Tomcat Server配置。

* IntelliJ中生成的class文件放到classes文件夹下。
* 如果需要第三方jar包，则会在WEB-INF下创建一个lib文件夹，用于保存jar包。
* 在应用程序根目录（与WEB-INF同级）下，还可以创建子文件夹，比如js,css之类，用于保
存各种Web资源
* 放在应用程序目录下的任何资源，用户只要输入资源URL，都可以直接访问到。如果
想让某一个资源可以被Servlet访问，但不可以被用户访问，那么就要把它放在WEB-INF目录下。
* 部署描述符总是命名为web.xml，并且放在WEB-INF目录下