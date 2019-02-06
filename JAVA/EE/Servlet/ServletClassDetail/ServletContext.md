#### ServletContext

1. ServletContext表示Servlet应用程序上下文。每个Web应用程序只有`一个上下文`。在将一个应用程序同时部署到多个容器的分布式环境中，每台Java虚拟机上的Web应用都会有一个ServletContext对象.
2. javax.servlet.ServletContext接口提供对servlet的Web应用程序变量的访问。 ServletContext是唯一对象，`可供Web应用程序中的所有servlet使用,当我们希望一些init参数可用于Web应用程序中的多个或所有servlet时`,我们可以使用ServletContext对象并使用<context-param>元素在web.xml中定义参数。 
3. 我们可以通过ServletConfig的getServletContext()方法获取ServletContext对象。

> 这一对象拥有以下重要的方法，可以用来保存各种数据：

* getAttribute
* getAttributeNames
* setAttribute
* removeAttribute

Servlet engines may also provide context objects that are unique to a group of servlets,并且该上下文对象与主机的URL路径命名空间的特定部分相关联。

注意：理想情况下，此接口的名称应为ApplicationContext，因为它适用于应用程序，
而不是特定于任何servlet。另外，不要将它与URL中传递的以访问Web应用程序的servlet上下文混淆。

```java
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
	
	public abstract Object getAttribute（String name） - 返回给定名称的object属性

	java.util.Enumeration<String> getAttributeNames() 公共抽象的方法获取所有属性的枚举

	public abstract void setAttribute（String paramString，Object paramObject） -
	 此方法用于设置具有应用程序范围的属性。有权访问此ServletContext的所有其他servlet都可以访问该属性。
	 我们可以使用public abstract void removeAttribute（String paramString）方法删除属性。
	 
	String getInitParameter（String name） - 此方法返回在web.xml中使用name定义的init参数的String值
	，如果参数名称不存在，则返回null。我们可以使用Enumeration <String> getInitParameterNames（）
	来获取所有init参数名称的枚举。
	
	boolean setInitParameter（String paramString1，String paramString2） - 我们可以使用此方法
	为应用程序设置init参数。
}
```