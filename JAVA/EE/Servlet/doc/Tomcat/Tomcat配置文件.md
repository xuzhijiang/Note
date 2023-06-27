# Tomcat配置文件

    Tomcat的配置文件默认存放在$CATALINA_HOME/conf目录中

1. server.xml： Tomcat核心配置文件，包含Service, Connector, Engine, Realm, Valve, Hosts主组件的相关配置信息。
2. context.xml：为部署在Tomcat实例上的web应用程序提供的默认配置文件，每个webapp都可以使用独有的context.xml,通常放置于webapp目录的META-INF子目录中，常用于定义会话管理器，Realm以及JDBC等。
3. web.xml：为部署在Tomcat实例上的所有web应用程序提供部署描述符，通常用于为webapp提供默认的servlet定义和基本的MUIME映射表。(定义了DefaultServlet和JspServlet,查看Tomcat/conf/web.xml)
4. tomcat-users.xml：Tomcat自带的manager默认情况下会用到此文件；在Tomcat中添加/删除用户，为用户指定角色等将通过编辑此文件实现。
5. logging.properties：定义日志相关的配置信息，如日志级别、文件路径等。

>注意: 所有的web应用程序在启动的时候都会加载 $CATALINA_HOME/conf/context.xml这个默认的context.xml文件,它里面会默认加载$CATALINA_HOME/conf/web.xml，web.xml定义了Tomcat提供的DefaultServlet，主要用来处理静态资源请求；当然对于每个自己的应用程序的web.xml也会被加载.
