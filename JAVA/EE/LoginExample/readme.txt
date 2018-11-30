所有servlet都必须实现javax.servlet.Servlet接口，该接口定义了servlet生命周期方法。
 实现通用服务时，我们可以扩展随Java Servlet API提供的GenericServlet类。
  HttpServlet类提供了诸如doGet（）和doPost（）之类的方法，用于处理特定于HTTP的服务。

大多数情况下，使用HTTP协议访问Web应用程序，这就是为什么我们主要扩展HttpServlet类。

Common Gateway Interface (CGI)：

在介绍Java Servlet API之前，CGI技术用于创建动态Web应用程序。
CGI技术有许多缺点（drawbacks），例如为每个请求创建单独的进程，
（platform dependent code）平台相关代码（C，C ++），高内存使用和低性能。

CGI与Servlet
引入Java Servlet技术来克服CGI技术的缺点。

Servlet在处理时间，内存利用率方面提供了更好的性能，因为servlet使用多线程的好处，
并且为每个请求创建一个新线程，这比使用CGI为每个请求加载创建新Object更快。

Servlet与平台和系统无关，使用Servlet开发的Web应用程序可以在任何标准Web容器上运行，
如Tomcat，JBoss，Glassfish服务器以及Windows，Linux，Unix，Solaris，Mac等操作系统。

Servlet是健壮的，因为容器负责servlet的生命周期，我们不需要担心内存泄漏，安全性，垃圾收集等。

Servlet是可维护的，学习曲线很小，因为我们需要注意的是我们的应用程序的业务逻辑。


Servlet API Hierarchy(Servlet API层次结构)

javax.servlet.Servlet是Servlet API的基本接口。 在使用Servlet时，我们应该注意一些其他接口和类。
 同样使用Servlet 3.0规范，servlet API引入了注释的使用，而不是在部署描述符中使用所有servlet配置。
  在本节中，我们将研究重要的Servlet API接口，类和注释，我们将在开发应用程序时进一步使用它们


Servlet接口

javax.servlet.Servlet是Java Servlet API的基本接口。
 Servlet接口声明了servlet的生命周期方法。 所有servlet类都需要实现此接口。

public interface Servlet {
	/**
	这是servlet容器调用以初始化servlet和ServletConfig参数的非常重要的方法。 
	除非init（）方法执行完毕，否则servlet尚未准备好处理客户机请求。 此方法在servlet
	生命周期中仅调用一次，并使Servlet类与普通java对象不同。 我们可以在servlet类中扩
	展此方法来初始化资源，例如DB Connection，Socket连接等。
	*/
	public void init(ServletConfig config) throws ServletException;
	
	/**
	此方法返回一个servlet配置对象，该对象包含此servlet的任何初始化参数和启动配置。
	 我们可以使用这个方法来获取部署描述符（web.xml）中的servlet定义的init参数，
	 或者通过Servlet 3中的注释来获取。稍后我们将研究ServletConfig接口。
	*/
	public ServletConfig getServletConfig();
	
	/**
	此方法负责处理客户端请求。 每当servlet容器收到任何请求时，它都会创建一个新线程并通过
	将请求和响应作为参数传递来执行service（）方法。 Servlet通常在多线程环境中运行，
	因此开发人员有责任使用synchronization保持共享资源的线程安全。
	*/
	public void service(ServletRequest req, ServletResponse res);
	
	/**
	此方法返回包含有关servlet的信息的字符串，例如其作者，版本和版权。 返回的字符串应该是纯文本，不能有标记。
	*/
	public String getServletInfo();
	
	/**
	此方法只能在servlet生命周期中调用一次，并用于关闭任何打开的资源。 这就像java类的finalize方法。
	*/
	public void destroy();
}

ServletContext interface

javax.servlet.ServletContext接口提供对servlet的Web应用程序变量的访问。 
ServletContext是唯一对象，可供Web应用程序中的所有servlet使用。 当我们希望一些
init参数可用于Web应用程序中的多个或所有servlet时，我们可以使用ServletContext对
象并使用<context-param>元素在web.xml中定义参数。 我们可以通过ServletConfig的
getServletContext（）方法获取ServletContext对象。

 Servlet engines may also provide context objects that are unique to a group of servlets 
 ，并且该上下文对象context object与主机的URL路径命名空间的特定部分相关联。

注意：理想情况下，此接口的名称应为ApplicationContext，因为它适用于应用程序，
而不是特定于任何servlet。 另外，不要将它与URL中传递的以访问Web应用程序的servlet上下文混淆。

public interface ServletContext {
	// Some of the important methods of ServletContext are:

	public abstract ServletContext getContext（String uripath） - 
	此方法返回特定uripath的ServletContext对象，如果不可用或对servlet不可见，则返回null。
	
	public abstract URL getResource（String path）throws MalformedURLException - 
	此方法返回URL对象，允许访问所请求的任何内容资源。我们可以访问项目，无论它们驻留在本地文件系统，远程文件系统，
	数据库还是远程网络站点，而不知道如何获取资源的具体细节。
	
	public abstract InputStream getResourceAsStream（String path） - 此方法将输入流返回给定资源路径，如果未找到则返回null。
	
	public abstract RequestDispatcher getRequestDispatcher（String urlpath） - 此方法主要用于获取对另一个servlet的引用。
	获取RequestDispatcher后，servlet程序员将请求转发给目标组件或包含来自它的内容。
	
	public abstract void log（String msg） - 此方法用于将给定的消息字符串写入servlet日志文件。
	
	public abstract Object getAttribute（String name） - 返回给定名称的object属性。
	我们可以使用公共抽象Enumeration <String> getAttributeNames（）方法获取所有属性的枚举。
	
	public abstract void setAttribute（String paramString，Object paramObject） -
	 此方法用于设置具有应用程序范围的属性。有权访问此ServletContext的所有其他servlet都可以访问该属性。
	 我们可以使用public abstract void removeAttribute（String paramString）方法删除属性。
	 
	String getInitParameter（String name） - 此方法返回在web.xml中使用name定义的init参数的String值
	，如果参数名称不存在，则返回null。我们可以使用Enumeration <String> getInitParameterNames（）
	来获取所有init参数名称的枚举。
	
	boolean setInitParameter（String paramString1，String paramString2） - 我们可以使用此方法
	为应用程序设置init参数。
}

ServletRequest interface

ServletRequest接口用于向servlet提供client请求信息。 Servlet容器从client request
创建ServletRequest对象，并将其传递给servlet service（）方法进行处理。

public interface ServletRequest {

	// returns the value of named attribute as Object and null if it’s not present. 
	// We can use getAttributeNames() method to get the enumeration of attribute 
	// names for the request. This interface also provide methods for setting and removing attributes.
	Object getAttribute（String name）
	
	String getParameter(String name) – This method returns the request parameter as 
	String. We can use getParameterNames() method to get the enumeration of parameter names for the request.
	
	String getServerName() – returns the hostname of the server.
	
	int getServerPort() – returns the port number of the server on which it’s listening.
	
}

ServletRequest的子接口是HttpServletRequest，它包含一些其他用于会话管理，cookie和请求授权的方法。
public interface HttpServletRequest extends ServletRequest {}

ServletResponse interface

servlet使用ServletResponse接口向客户端发送响应。 
Servlet容器创建ServletResponse对象并将其传递给servlet service（）方法，
然后使用response object为客户端生成HTML响应。

public interface ServletResponse {
	void addCookie(Cookie cookie) – Used to add cookie to the response.
	
	void addHeader(String name, String value) – used to add a response header 
	with the given name and value.
	
	String encodeURL(java.lang.String url) – encodes the specified URL by 
	including the session ID in it, or, if encoding is not needed, returns the URL unchanged.
	
	String getHeader(String name) – return the value for the specified header, 
	or null if this header has not been set.
	
	void sendRedirect(String location) – used to send a temporary redirect 
	response to the client using the specified redirect location URL.
	
	void setStatus(int sc) – used to set the status code for the response.
}

RequestDispatcher interface

RequestDispatcher接口用于将请求转发到另一个资源，该资源可以是同一上下文中的HTML，
JSP或其他servlet。 我们还可以使用它将另一个资源的内容包含在响应中。 
此接口用于同一上下文中的servlet通信。

RequestDispatcher interface is used to forward the request to another 
resource that can be HTML, JSP or another servlet in the same context.
 We can also use this to include the content of another resource to the 
 response. This interface is used for servlet communication within the 
 same context.
 
我们可以使用ServletContext getRequestDispatcher（String path）方法在servlet中
获取RequestDispatcher。 路径必须以/开头，并且被解释为相对于当前上下文根。

public interface RequestDispatcher {
	void forward（ServletRequest请求，ServletResponse响应） - 将请求从servlet转发到服务器上的另一个资源（servlet，JSP文件或HTML文件）。
	
	void include（ServletRequest请求，ServletResponse响应） - 包括响应中的资源（servlet，JSP页面，HTML文件）的内容。
}

GenericServlet class

GenericServlet是一个实现Servlet，ServletConfig和Serializable接口的抽象类。 
GenericServlet提供了所有Servlet生命周期方法和ServletConfig方法的默认实现，
并且在扩展此类时使我们的生活更轻松，我们只需要覆盖我们想要的方法，其余的我们可以使用默认实现。
 此类中定义的大多数方法仅用于轻松访问Servlet和ServletConfig接口中定义的公共方法。

GenericServlet类中一个重要的方法是无参数的init（）方法，如果我们必须在处理来自servlet的任何请
求之前初始化一些资源，我们应该在servlet程序中覆盖这个方法。

public abstract class GenericServlet implements Servlet, ServletConfig{}


HTTPServlet class

HTTPServlet是一个抽象类，它扩展了GenericServlet，并为创建基于HTTP的Web应用程序提供了基础。 
有些方法被子类重写为不同的HTTP方法。

public abstract class HttpServlet extends GenericServlet {
	doGet(), for HTTP GET requests
	doPost(), for HTTP POST requests
	doPut(), for HTTP PUT requests
	doDelete(), for HTTP DELETE requests
};


Servlet Attributes

Servlet属性用于servlet间通信，我们可以在Web应用程序中设置，获取和删除属性。
 servlet属性有三个范围 - 请求范围，会话范围和应用程序范围。
 
ServletRequest，HttpSession和ServletContext接口提供了分别从请求，会话和应用程序范围获取/设置/删除属性的方法。

Servlet属性与web.xml中为ServletConfig或ServletContext定义的init参数不同。


Annotations in Servlet 3

Prior to Servlet 3(在Servlet 3之前),所有servlet映射及其init参数都用于在web.xml中定义，
当应用程序中的servlet数量巨大时，这不方便且更容易出错。

Servlet 3引入了使用java注释来定义servlet，过滤器和监听器servlet以及init参数。

一些重要的Servlet注释是：

WebServlet - 我们可以将这个注释与Servlet类一起使用来定义init parameters, loadOnStartup value, 
description and url patterns etc.至少一个URL pattern在annotation的value或urlPattern属性中声明
但不能同时在两者中声明。声明此批注的类必须扩展HttpServlet。

WebInitParam - 此注释用于定义servlet或filter的init参数，它包含name, value pair and we can provide description also. 
此批注可以在WebFilter或WebServlet批注中使用。 This annotation can be used within a WebFilter or WebServlet annotation.

WebFilter - 此注释用于声明servlet过滤器。该注释在部署期间由容器处理，其中找到它的Filter类将根据配置创建并应用于URL模式，
Servlet和DispatcherTypes。带注释的类必须实现javax.servlet.Filter接口。
the Filter class in which it is found will be created as per the configuration and applied to 
the URL patterns, Servlets and DispatcherTypes. 

WebListener - 用于在给定的Web应用程序上下文中为各种类型的事件声明侦听器的注释。

注意：我们将在以后的文章中研究Servlet过滤器和监听器，在本文中我们的重点是学习Servlet API的基接口和类。

