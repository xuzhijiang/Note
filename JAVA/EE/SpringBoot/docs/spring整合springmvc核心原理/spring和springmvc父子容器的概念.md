# spring和springmvc父子容器的概念

在spring和springmvc进行整合的时候，一般情况下我们会使用不同的配置文件来配置spring(使用root-context.xml)和springmvc(使用servlet-context.xml),因此我们的应用中会存在至少2个ApplicationContext实例(分别加载这两个xml得到的)，

    由于是在web应用中，因此最终实例化的是ApplicationContext的子接口WebApplicationContext.

![](../pics/spring-springmvc.png)

    图显示了2个WebApplicationContext实例，为了进行区分，分别称之为：
    
    Root WebApplicationContext: 父容器(根容器-rootIoC容器):用来保存Services/Repositories(dao)这2个组件
    Servlet WebApplicationContext: 子容器(简称web容器):用来保存Controllers/ViewResolver(视图解析器)/文件上传下载的/消息转换器,以及其他和web相关的beans.

    父子关联之后,子容器就可以访问父容器的组件了.
    子容器可以引用父容器的services/dao,这也就是为什么子容器中的cotnroller可以注入service,就是因为这个父子引用关系才可以注入.

# Servlet WebApplicationContext

Servlet WebApplicationContext：这是对J2EE三层架构中的web层进行配置，
如控制器(controller)、视图解析器(view resolvers)等相关的bean。
通过spring mvc中提供的DispatchServlet来加载配置，通常情况下，配置文件的名称为servlet-context.xml。

# Root WebApplicationContext

Root WebApplicationContext：这是对J2EE三层架构中的service层、dao层进行配置，如业务bean，数据源(DataSource)等。通常
配置文件名称为root-context.xml。在web应用中，其一般通过`ContextLoaderListener`来加载。

# 父子容器的作用

当我们尝试从child context(即：Servlet WebApplicationContext)中获取一个bean时，如果找不到，则会委派给parent context (即Root WebApplicationContext)来查找。

如果我们没有通过ContextLoaderListener来创建Root WebApplicationContext，那么Servlet WebApplicationContext的parent就是null，也就是没有parent context。

# 为什么要有父子容器?为什么spring要这么设计呢?

    7大软件设计原则中有一个职责单一原则
    
笔者理解，父子容器的作用主要是划分框架边界:

在J2EE三层架构中，在service层我们一般使用spring框架，而在web层则有多种选择，如springmvc、struts等。因此，通常对于web层我们会使用单独的配置文件

例如在下面的案例中，一开始我们使用servlet-context.xml来配置web层，
使用root-context.xml来配置service、dao层。如果现在我们想把web层从spring mvc替换成struts，那么只需要将servlet-context.xml替换成Struts的配置文件struts.xml即可，而root-context.xml不需要改变。
