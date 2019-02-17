## 我们将研究servlet filter的各种用法

Java Servlet Filter用于拦截请求(intercept request)并进行一些预处理(pre-process)，可用于拦截响应intercept response并在发送到客户端之前进行后处理(post-process). 

### Why do we have Servlet Filter?

之前,我们学习了如何在Web应用程序中管理会话，we learnd how we can manage session in web application.如果我们想确保只有在用户session is valid时才能访问资源，我们可以使用servlet session属性来实现,
这个方法很简单，但如果我们有很多servlet和jsps，那么由于冗余代码，它将变得难以维护。
如果我们希望将来更改这些session属性的name，
我们将不得不更改我们进行会话身份验证(session authentication)的所有位置。

这就是为什么我们有servlet过滤器。 
Servlet过滤器是可插入的Java组件，我们可以使用它在request被发送到servlet之前拦截和处理请求，并在servlet代码完成之后和容器将响应发送回客户端之前进行对响应做处理。

#### 我们可以使用servlet过滤器执行的一些常见任务是：

1. 将请求参数记录到日志文件。
2. 资源请求的身份验证和自动化。
3. 在将request body or header发送到servlet之前格式化。
4. 压缩发送给客户端的响应数据。
5. 通过添加一些cookie，标题信息等来改变响应。

正如我前面提到的，servlet filter是可插入的，并在deployment descriptor file中配置，`Servlet和filter彼此都是没有意识的`，我们可以通过编辑web.xml来添加或删除servlet过滤器。

我们可以为单个资源提供多个过滤器，我们可以在web.xml中为单个资源创建一系列过滤器。 


### 如何创建Servlet Filter

> javax.servlet.Filter接口类似于Servlet接口,我们需要实现它来创建我们自己的servlet过滤器。

> Servlet Filter接口包含Filter的`生命周期方法`，它由`servlet容器管理`。

```java
public interface Filter {

	/**
	* 当容器初始化Filter时，这个方法被调用。此方法在过滤器的生命周期中只调用一次，this method is called only once in the lifecycle of filter.
	我们应该在此方法中初始化任何资源。容器使用FilterConfig向Filter提供init参数和servlet上下文对象。
	我们可以在这个方法中抛出ServletException。
	*/
	void init(FilterConfig paramFilterConfig) 

	/**
	*   这个方法每次由容器在必须将过滤器应用于资源时调用。 Container提供请求和响应对象引用给过滤器作为参数。 
	* FilterChain用于调用链中的下一个过滤器。这是责任链模式的一个很好的例子。
	*/
	doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
	
	/**
	* 当容器卸载Filter实例时，它会调用destroy(）方法,这是我们可以关闭过滤器打开的任何资源的方法。 此方法在过滤器的生命周期中仅调用一次。
	*/
	void destroy() 
}
```

### Servlet WebFilter注释

在Servlet 3.0中引入了javax.servlet.annotation.WebFilter，
我们可以使用这个注释声明一个servlet过滤器。 我们可以使用此批注来定义init参数，过滤器名称和描述，servlet，url patterns and dispatcher types to apply the filter.

如果频繁更改过滤器配置，最好使用web.xml，因为这不需要您重新编译过滤器类。
If you make frequent changes to the filter configurations, its better to use web.xml because that will not require you to recompile the filter class.

这就是Java中的Servlet过滤器,它是J2EE Web应用程序的一个重要特性，我们应该将它用于各种servlet执行的常见任务.

> Struts 2使用Servlet Filter拦截客户端请求并将它们转发到适当的动作类,这些动作类称为Struts 2 Interceptor