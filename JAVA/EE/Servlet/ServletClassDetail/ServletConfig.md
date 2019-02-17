#### ServletConfig

javax.servlet.ServletConfig用于将配置信息传递给Servlet。

1. 当Servlet容器初始化Servlet时， Servlet容器会给Servlet的init方法
传入一个ServletConfig对象.
2. ServletConfig封装了通过@WebServlet或者部署描述符(即web.xml）
传给Servlet的配置信息。这样传入的每一条信息就叫一个初始参数。一个
初始参数有key和value两个组成部分。可以调用getInitParameter方法
读取这些配置参数值.
3. 每个servlet都有自己的ServletConfig对象，servlet容器负责实例化这个对象。
4. 注意区分ServletConfig和ServletContext,前者是针对某一个Servlet的，后者是针对一组Servlet的.

通常有两种方式配置Servlet：

1. 在web.xml中以XML元素方式进行配置
2. 直接在Servlet中使用注解进行配置

我们可以在web.xml文件中提供servlet init参数, 或通过使用
WebInitParam注释提供servlet init参数

我们可以使用Servlet的getServletConfig()方法来获取初始化此servlet的ServletConfig对象。

public interface ServletConfig {

	// The important methods of ServletConfig interface are:
	
	/**
	此方法返回servlet的ServletContext对象。 我们将在下一节中研究ServletContext接口。
	*/
	public abstract ServletContext getServletContext();
	
	/**
	此方法返回为servlet定义的init参数名称的Enumeration <String>。 如果没有定义init参数，
	则此方法返回空枚举。
	*/
	public abstract Enumeration<String> getInitParameterNames() 

	/**
	此方法可用于按名称获取特定的init参数值。 如果the initialization parameter does not exist，则返回null。
	*/
	public abstract String getInitParameter(String paramString)
}