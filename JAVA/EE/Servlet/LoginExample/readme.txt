## Servlet

如果你深刻理解了Servlet的生命周期，就可以在底层做很多事情。在Request进来的时候，
进行拦截，进行权限的判定。也可以在Response发给client的时候，进行拦截，统一检查、统一附加。
所以Servlet不仅要学，而且要学深，学透。”

### Common Gateway Interface (CGI)

在介绍Java Servlet API之前，先介绍一下早期使用的Web服务器扩展机制是CGI(公共网关接口)，web服务器收到请求后，将request转给CGI，CGI处理后，将产生一个响应，该响应会返回给web服务器，web服务器对response进行包装，以HTTP响应的方式返回给浏览器.
CGI技术有许多缺点（drawbacks），例如为每个请求创建单独的进程，
(platform dependent code)平台相关代码（C，C++），高内存使用和低性能。

### Servlet与Servlet容器

引入Java Servlet技术来克服CGI技术的缺点。

Java Servlet(Java服务器小程序)是一个基于Java技术的Web组件，运行在服务器端，它由Servlet容器所管理，用于生成动态的内容。 Servlet是平台独立的Java类(jdk中的jvm是和平台相关的，java是运行在jvm上，编写一个Servlet，实际上就是按照Servlet规范编写一个Java类。Servlet被编译为平台独立 的字节码，可以被动态地加载到支持Java技术的Web服务器中运行。 

Servlet容器也叫做Servlet引擎，是Web服务器的一部分，Servlet没有main方法，不能独立运行，它必须被部署到Servlet容器中，由容器来实例化和调用Servlet的方法（如doGet()和doPost()），Servlet容器在Servlet的生命周期内包容和管理Servlet。在JSP技术 推出后，管理和运行Servlet/JSP的容器也称为Web容器。

有了servlet之后，用户在浏览器的地址栏中输入URL发来请求，Web服务器接收到该请求后，并不是将请求直接交给Servlet，而是交给Servlet容器。当Servlet容器接受到Web服务器传来的请求，发现处理请求的资源是Servlet时，然后Servlet容器(Web容器)创建两个对象HTTPServletRequest和HTTPServletResponse, 然后，它根据URL找到正确的servlet, 并为请求创建一个线程, Servlet容器实例化Servlet，然后它调用servlet service()方法, 然后基于HTTP method，service()方法调用doGet()或doPost()方法对请求进行处理,Servlet方法生成动态页面并将其写入response, servlet线程完成后,这个response由Servlet容器返回给Web服务器，Web服务器包装这个响应，以HTTP响应的形式发送给Web浏览器。

Web browser ---> Web Server ------> servlet Container(web container) ----> GenericServlet ---> HttpServlet ---> OurServelt ----> find resource and generate response ---> servlet Container(web container)----> Web Server ----> Web browser

#### servlet容器(web容器)能提供什么？

我们知道需要由servlet容器来管理和运行servlet，但是为什么要这样做呢？使用servlet容器的原因有：

	Communication Support(通信支持) - Servlet容器提供“Web服务器”与“servlet和JSP”之间的简单通信方式。
	而不需要构建server socket来侦听来自Web服务器的任何请求，解析请求并生成响应。
	所有这些重要且复杂的任务都是由容器完成的，
	容器知道自己与web服务器之间的协议，所以你的servlet不用担心web服务器（如Apache）和你自己的web代码之间的API，只需要考虑如何在servlet中实现业务逻辑（如处理一个订单）

	Lifecycle and Resource Management (生命周期和资源管理) - servlet Container负责管理(take care of managing the
	life cycle of servlet.)servlet的生命周期。 
	Container负责将servlet类加载到内存中，初始化servlet，调用servlet方法并销毁它们。
	Container还提供JNDI等实用程序，用于资源池和管理,有了servlet容器，你不需要太多的考虑资源管理

	Multithreading Support (多线程支持 ) - 容器为每个到这个servlet的请求创建一个新线程，当每个请求处理完成时(servlet已经运行完相应的http服务方法)，线程dies.
	因此，servlet不会针对每个请求进行初始化，也就是多个请求共用一个servlet,
	从而节省了时间和内存。这并不是说你不需要考虑线程安全性，其实你还会遇到同步问题，不过这样能使你少做很多工作.

	JSP支持 - JSP看起来不像普通的java类，Web容器提供对JSP的支持。应用程序中的每个JSP都由容器
	编译并转换为Servlet，然后容器像其他管理servlet一样管理它们。
	Every JSP in the application is compiled by container and converted to 
	Servlet and then container manages them like other servlets.

	其他任务Miscellaneous Task  - Web容器管理资源池，执行内存优化，运行垃圾收集器，提供安全配置，支持多个应用程序，
	热部署以及场景背后的其他几项任务，使我们的生活更轻松。

	Servlet是Java EE服务器驱动的技术，用于在Java中创建Web应用程序。 javax.servlet和javax.servlet.http包提供了用于编写我们自己的servlet的接口和类。

	所有servlet都必须实现javax.servlet.Servlet接口，该接口定义了servlet生命周期方法。
	 实现通用服务时，我们可以扩展随Java Servlet API提供的GenericServlet类。
	 HttpServlet类提供了诸如doGet（）和doPost（）之类的方法，用于处理特定于HTTP的服务。

	大多数情况下，使用HTTP协议访问Web应用程序，这就是为什么我们主要扩展HttpServlet类。

### 与CGI程序相比，Servlet具有以下优点:

1. Servlet是单实例多线程的运行方式，每个请求在一个独立的线程中运行，而提供服务的Servlet实例只有一个
2. Servlet使用标准的API，被更多的Web服务器所支持。 
3. Servlet使用Java编写，因此拥有Java程序语言的所有优点，包括容易开发和平台独立性。 
4. Servlet在处理时间，内存利用率方面提供了更好的性能，因为servlet使用多线程的好处，
并且为每个请求创建一个新线程，这比使用CGI为每个请求加载创建新对象更快。
5. Servlet与平台和系统无关，使用Servlet开发的Web应用程序可以在任何标准Web容器上运行，
如Tomcat，JBoss，Glassfish服务器以及Windows，Linux，Unix，Solaris，Mac等操作系统。
6. Servlet是健壮的，因为容器负责servlet的生命周期，我们不需要担心内存泄漏，安全性，垃圾收集等。
7. Servlet是可维护的，学习曲线很小，因为我们需要注意的是我们的应用程序的业务逻辑。

### Servlet容器的分类(基于工作模式)

1. 独立的Servlet容器

当我们使用基于Java技术的Web服务器时，Servlet容器作为构成Web服务器的一部分而存在。然而大多数的Web服务器并非基于Java，因此，就有了下面两种Servlet容器的工作模式。

2. 进程内的Servlet容器

Servlet容器由Web服务器插件和Java容器两部分的实现组成。Web服务器插件在某个Web服务器内部地址空间中打开一个JVM(Java虚拟机），使得Java容器可以在此JVM中加载并运行Servlet。如有客户端调用Servlet的请求到来，插件取得对此请求的控制并将它传递（使用JNI技术）给Java容器，然后由Java容器将此请求交由Servlet进行处理。进程内的Servlet容器对于单进程、多线程 的服务器非常适合，提供了较高的运行速度，但伸缩性有所不足。

3. 进程外的Servlet容器

Servlet容器运行于Web服务器之外的地址空间，它也是由Web服务器插件和Java容器两部分的实现组成的。Web服务器插件和Java容器（在外部JVM中运行）使用IPC机制（通常是TCP/IP）进行通信。当一个调用Servlet的请求到达时，插件取得对此请求的控制并将其传递（使用IPC机制）给Java容器。进程外Servlet容器对客户请求的响应速度不如进程内的Servlet容器，但进程外容器具有更好的伸缩性和稳定性。

### Tomcat

学习Servlet技术，就需要有一个Servlet运行环境，也就是需要有一个Servlet容器，本文用的是Tomcat。Tomcat是一个免费的开放源代码的Servlet容器,最新的Servlet和 JSP规范总是能在Tomcat中得到体现,Tomcat和IIS、Apache等Web服务器一样，具有处理HTML页面的功能，另外它还是一个Servlet和JSP容器，独立的 Servlet容器是Tomcat的默认模式。不过，Tomcat处理静态HTML的能力不如Apache，我们可以将Apache和Tomcat集成在一起使用，Apache作为HTTP Web服务器，Tomcat作为Web容器。

Tomcat服务器接受客户请求并做出响应的过程如下：

1）客户端（通常都是浏览器）访问Web服务器，发送HTTP请求。 
2）Web服务器接收到请求后，传递给Servlet容器。 
3）Servlet容器加载Servlet，产生Servlet实例后，向其传递表示请求和响应的对象。 
4）Servlet实例使用请求对象得到客户端的请求信息，然后进行相应的处理。 
5）Servlet实例将处理结果通过响应对象发送回客户端，容器负责确保响应正确送出，同时将控制返回给Web服务器。

#### Tomcal conf/server.xml(简化版)

1. 核心组件是Catalina Servlet容器，它是所有其他Tomcat组件的顶层容器
2. Server元素表示整个Catalina servlet容器。
3. Server中可以有多个Service。
4. Service元素表示一或多个连接器组件的组合，这些组件共享一个用于处理传入请求的引擎组件
5. Executor表示可以在Tomcat中的组件之间共享的线程池。
6. Connector代表连接组件。Tomcat 支持三种协议：HTTP/1.1、HTTP/2.0、AJP。
7. Context元素表示一个Web应用程序，它在特定的虚拟主机中运行。每个Web应用程序都基于Web应用程序存档（WAR）文件，或者包含相应的解包内容的相应目录
8. Engine元素表示与特定的Catalina服务相关联的整个请求处理机器。它接收并处理来自一个或多个连接器的所有请求，并将完成的响应返回给连接器，以便最终传输回客户端。
9. Host元素表示一个虚拟主机，它是一个服务器的网络名称（如“www.mycompany.com”）与运行Tomcat的特定服务器的关联。

<?xml version="1.0" encoding="UTF-8"?>
<Server port="8005" shutdown="SHUTDOWN">
  <Service name="Catalina">
    <!--The connectors can use a shared executor, you can define one or more named thread pools-->
    <!--
    <Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
        maxThreads="150" minSpareThreads="4"/>
    -->
    <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
	<!-- A "Connector" using the shared thread pool-->
    <!--
    <Connector executor="tomcatThreadPool"
               port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
    -->
    <!-- Define an AJP 1.3 Connector on port 8009 -->
    <Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />
    <Engine name="Catalina" defaultHost="localhost">
      <!-- Use the LockOutRealm to prevent attempts to guess user passwords
           via a brute-force attack -->
      <Realm className="org.apache.catalina.realm.LockOutRealm">
        <Realm className="org.apache.catalina.realm.UserDatabaseRealm"
               resourceName="UserDatabase"/>
      </Realm>
      <Host name="localhost">
         <Context path="" docBase="WORKDIR" reloadable="true"/>  
      </Host>
    </Engine>
  </Service>
</Server>

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

