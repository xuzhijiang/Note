学习Servlet的关键是了解它的几个核心类型的功能和它所封装的信息，其实还是挺简单的，只要你对HTTP协议了解得不错.

### Servlet API有以下4个Java包:

1. javax.servlet: 包含定义Servlet和Servlet容器之间契约
的类和接口。
2. javax.servlet.http: 包含定义HTTP Servlet和Servlet容器之
间契约的类和接口。
3. javax.servlet.annotation: 包含标注Servlet、 Filter、 Listener的
注解，它还为被标注的组件定义元数据。
4. javax.servlet.descriptor: 包含提供程序化登录web应用程序的配置信
息的类型。

#### Javax.servlet中的主要类型

1. Servlet(接口) <------	 GenericServlet
2. ServletRequest(接口)
3. ServletResponse(接口)
4. ServletContext(接口)
5. ServletConfig(接口)
6. RequestDispatcher(接口)
7. Filter(接口)
#### 基本原理

1. Servlet容器将Servlet类载入内存，并在Servlet实例上调用具体的方法。在
一个应用程序中，每种Servlet类型只能有一个实例(Singleton Object).
2. 用户请求使Servlet容器调用Servlet的Service方法，并传入一个
ServletRequest实例（封装HTTP请求）和一个ServletResponse实例（封装
HTTP响应）
3. 对于每一个应用程序，Servlet容器还会创建一个ServletContext实例。这个
对象中封装了上下文（应用程序）的环境详情

### Servlet API Hierarchy(Servlet API层次结构)

1. javax.servlet.Servlet是Servlet API的基本接口。 
2. 同样使用Servlet 3.0规范，servlet API引入了注释的使用，
而不是在部署描述符中使用所有servlet配置。
3. Servlet接口声明了servlet生命周期中必不可少的三个方法-init()、service()和destroy()
4. 所有servlet类都需要实现此接口来实现这3个方法,而且由服务器在特定的时刻调用。
5. 和所有的Java程序一样，servlet运行在JVM中。
6. Web应用是多线程的，而Servlet是Singleton的，因此，访
问它的字段要考虑到线程安全问题。

```java
public interface Servlet {
	/**
	1. init()方法在servlet生命周期的初始化阶段被调用。
	2. 它传递一个实现了javax.servlet.ServletConfig接口的对象，使得servlet能够从web application中获取初始化参数
	3. 这是servlet容器调用以初始化servlet和ServletConfig参数的非常重要的方法。 
	4. 除非init()方法执行完毕，否则servlet尚未准备好处理客户机请求。
	5. 此方法在servlet生命周期中仅调用一次，并使Servlet类与普通java对象不同。 
	6. 我们可以在servlet类中扩展此方法来初始化资源，例如DB Connection，Socket连接
	*/
	public void init(ServletConfig config) throws ServletException;
	
	/**
	此方法返回一个servlet配置对象，该对象包含此servlet的任何初始化参数和启动配置。
	 我们可以使用这个方法来获取部署描述符（web.xml）中的servlet定义的init参数，
	 或者通过Servlet 3中的注释来获取。稍后我们将研究ServletConfig接口。
	*/
	public ServletConfig getServletConfig();
	
	/**
	0. 最重要的是service方法，用于生成HTTP响应
	1. servlet初始化之后,每接收一个请求，就会调用service()方法.
	2. 每个请求的处理都在独立的线程中进行。
	3. Web服务器对每个请求都会调用一次service()方法。
	4. service()方法判断请求的类型，并把它转发给相应的方法进行处理。
	5. 此方法负责处理客户端请求。 
	6. 每当servlet容器收到任何请求时，它都会创建一个新线程并通过
	将请求和响应作为参数传递来执行service（）方法。 
	7. Servlet通常在多线程环境中运行，
	因此开发人员有责任使用synchronization保持共享资源的线程安全。
	*/
	public void service(ServletRequest req, ServletResponse res);
	
	/**
	此方法返回包含有关servlet的信息的字符串，例如其作者，版本和版权。 返回的字符串应该是纯文本，不能有标记。
	*/
	public String getServletInfo();
	
	/**
	 此方法只能在servlet生命周期中调用一次，并用于关闭任何打开的资源。 这就像java类的finalize方法。该方法释放被占用的资源.
	*/
	public void destroy();
}
```

### Servlet Attributes

Servlet属性用于servlet间通信，我们可以在Web应用程序中设置，获取和删除属性。
servlet属性有三个范围 - 请求范围，会话范围和应用程序范围。

ServletRequest，HttpSession和ServletContext接口提供了分别从请求，会话和应用程序范围获取/设置/删除属性的方法。

Servlet属性与web.xml中为ServletConfig或ServletContext定义的init参数不同。

Annotations in Servlet 3(Servlet3中的注解)

Prior to Servlet 3(在Servlet 3之前),所有servlet映射及其init参数都用于在web.xml中定义，
当应用程序中的servlet数量巨大时，这不方便且更容易出错。

Servlet 3引入了使用java注释来定义servlet，filter(过滤器)和listener(监听器)servlets以及init参数。

一些重要的Servlet注释是：

WebServlet:
	1. 声明此批注的类必须extends HttpServlet。The class on which this annotation is declared MUST extend HttpServlet.
	2. 我们可以将这个注释与Servlet类一起使用来定义init parameters, loadOnStartup value(大概就是在
	开始的时候加载的值), description and url patterns etc.
	3. 在WebServlet这个注解的value属性，或者是WebServlet的urlPatterns属性至少声明一个url匹配模式
	，但是不能同时声明两者。At least one URL pattern MUST be declared in either the value or urlPattern
	 attribute of the annotation, but not both.

WebInitParam：
	1. 此注释用于定义servlet或filter的init参数，它包含name, value pair 
	and we can provide description also. 
	2. 此批注可以在WebFilter注解或WebServlet注解中使用。 
	This annotation can be used within a WebFilter or WebServlet annotation.

WebFilter:
	1. 此注释用于声明servlet过滤器(filter)。该注释在部署期间由容器处理，
	其中找到它的Filter类将根据配置创建并应用于URL模式，Servlet和DispatcherTypes。
	the Filter class in which it is found will be created as per the configuration and applied to 
	the URL patterns, Servlets and DispatcherTypes.
	
	2.带WebFilter注解的类 MUST implements javax.servlet.Filter interface。
	 

WebListener:
	1. 用于在 in a given(给定的) Web application context(上下文) 中为 
	各种类型的事件 (for various types of event)声明侦听器(listener)的注解。

注意：我们将在以后的文章中研究Servlet过滤器和监听器，在本文中我们的重点是学习Servlet API的基接口和类。
