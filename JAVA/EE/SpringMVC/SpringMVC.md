Spring MVC框架广泛用于java Web应用程序。

与Struts Framework一样，Spring MVC也基于Java EE Servlet和JSP技术，
并实现了Model-View-Controller设计模式(MVC)。

@RequestMapping与类classes和方法methods一起使用，
将客户端请求重定向到特定的处理程序方法(handler method)。 
请注意，处理程序(handler method)方法返回String，这应该是要用作响应的
视图页面的名称(the name of view page)。

@Validated注释标记一个model类，对模型类进行数据验证。

文件上传是任何Web应用程序中非常常见的任务。 我们可以使用Servlet和Struts2实现文件上传。 我们将学习Spring文件上传，特别是单个和多个文件的Spring MVC文件上传。

<annotation-driven />标签启用了Controller编程模型，没有它Spring不会将HomeController识别为客户端请求的处理程序。

<context:component-scan base-package="com.journaldev.spring" />：提供了一个包，Spring将在这包中查找带注释的组件并将它们自动注册为Spring bean。


