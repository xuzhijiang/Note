# 什么是JavaEE规范?

    SUN公司的JavaEE规范有很多,包括：JDBC，JMS(Java消息服务)，EJB(企业Java对象)，RMI，
    Java Server Pages（JSP），Servlets，JavaServer Faces(JSF)，JPA(Java持久化), JavaMail等等.
    
    Servlets和JSP只是Java EE规范中的两个

# JavaEE和JavaWeb的区别?

    还有一一个组织，专门搞开源的，叫Apache。搞出了一个叫Tomcat的服务器。
    
    Tomcat也没把所有的JavaEE规范都实现，Tomcat只实现了部分,其中最重要的就是Servlet和JSP。
    这些规范实现都下载Tomcat安装包后默认提供的.
    
    此外，tomcat官网还提供了另外一些Java EE规范的实现，如JMX Remote等，要使用的话需要另外下载相关jar包，
    放到Tomcat安装目录/lib中.可以说Tomcat是一个不完整的Java EE应用服务器
    
    而其他服务器比如JBoss、Weblogic都是完全支持JavaEE规范的。所以人们往往更愿意叫Tomcat为Servlet容器

    听到这，你不禁大叫：不对啊，我记得自己写的程序里有用到JMS啊，还可以运行哩！

    那是因为你自己手动导了JMS包...但是你安装了Tomcat后另外导过Servlet/JSP的包吗？没有嘛！
    人家tomcat自己按照JavaEE规范实现了Servlet/JSP规范，都整到自己Tomcat源码里了。

>所以我更愿意称自己是JavaWeb程序员，而不是JavaEE程序员。JavaEE其实很重.

#  Apache Tomcat

Tomcat是一个Java WEB应用服务器,注意是Java Web应用服务器不是Java EE应用服务器,这是有区别的,Tomcat也被称为Servlet容器，但不是Java EE容器.因为Tomcat只实现了部分JavaEE规范.

    Tomcat是用Java语言编写的，需要运行在Java虚拟机上，所以一般需要先安装JDK，以提供运行环境。

    Tomcat服务器 = 拥有Web服务器的功能(也就是处理静态资源请求) + Servlet/JSP容器（Web容器的功能）
