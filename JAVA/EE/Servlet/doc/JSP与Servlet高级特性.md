# JSP与Servlet高级特性

* 异步处理

这三个特性在现代Web后端开发框架中得到了普遍的支持。特别是异步处理，更是发展态势良好，各Web后端开发框架都纷纷在这块推出新技术。

本讲所介绍的这三个技术特性是针对Servlet来说的，请注意它们与最新的Spring Boot 2.0所引入的技术之间是有差异的，但基本思想和原理则没有太多的变化。

另外，本讲还介绍了一个比较综合的实例——用于网站登录的图形验证码。

这个实例综合应用了本课程所介绍的大多数Servlet和JSP技术，能把它搞懂，就可以说你对Servlet和JSP技术的学习是有成效的，可以进入下一模块的学习了。

### 监听器

> 示例： ServletAndJSPAdvanced

Servlet API提供了一系列的`事件和事件监听接口`。

上层的servlet/JSP应用能够通过调用这些API进行事件驱动的开发。
这里监听的所有事件都继承自`java.util.Event对象`。

监听器接口可以分为三类： 

* ServletContext
* HttpSession
* ServletRequest

#### 监听器接口

监听器接口主要在javax.servlet和javax.servlet.http的包中。有以下这些接口:

接口 					说明
ServletContextListener  它能够响应ServletContext生命周期事件，它提供了
						ServletContext创建之后和ServletContext关闭之前的会被调用的方法。

ServletContextAttributeListener 它能够响应ServletContext范围的属性添加、删除、替换
事件。

HttpSessionListener 	它能够响应HttpSession的创建、超时和失效事件。

HttpSessionAttributeListener  它能响应HttpSession范围的属性添加、删除、替换事件。

HttpSessionActivationListener 它在一个HttpSession激活或者失效时被调用

HttpSessionBindingListener 	可以实现这个接口来保存HttpSession范围的属性。当有属性从HttpSession添加或删除时，HttpSessionBindingListener接口能够做出响应。

ServletRequestListener    它能够响应一个ServletRequest的创建或删除。

ServletRequestAttributeListener  它能响应ServletRequest范围的属性值添加、删除、修改事件。

AsyncListener 一个用于异步操作的监听器

### 过滤器

1. Filter是拦截Request请求的对象：在用户的请求访问资源前处理ServletRequest以及ServletResponse，它可用于日志记录、加解密、Session检查、图像文件保护等。
2. 通过Filter可以拦截处理某个资源或者某些资源。
3. Filter的配置可以通过Annotation或者部署描述(web.xml）来完成。当一个资源或者某些资源需要被多个Filter所使用到，且它的触发顺序很重要时，只能通过部署描述来配置。

#### Filter的生命周期

1. Filter的实现必须实现javax.servlet.Filter接口。 这个接口包含了Filter的3个生命周期： init、 doFilter、 destroy。
2. Servlet容器初始化Filter时，会触发Filter的init方法，一般来说是在应用开始时。也就是说， init方法并不是在该Filter相关的资源使用到时才初始化的， 而且这个方法只调用一次，用于初始化Filter。
3. 当Servlet容器每次处理Filter相关的资源时，都会调用该Filter实例的doFilter方法， 在Filter的doFilter的实现中，最后一行需要调用FilterChain中的doChain方法。
4. destory方法在Servlet容器要销毁Filter时触发，一般在应用停止的时候进行调用

#### Filter链条

一个资源可能需要被多个Filter关联到(更专业一点来说，这应该叫作Filter链条），这时Filter.doFilter()的方法将触发Filter链条中下一个Filter。只有在Filter链条中最后一个Filter里调用的FilterChain.doFilter()，才会触发处理资源的方法。

如果在Filter.doFilter()的实现中，没有在结尾处调用FilterChain.doFilter()的方法，那么该Request请求中止，后面的处理就会中断。

#### 线程安全问题

除非Filter在部署描述中被多次定义到，否则Servlet容器只会为每个Filter创建单一实例。由于Serlvet/JSP的应用通常要处理用户并发请求，此时Filter实例需要同时被多个线程所关联到，因此需要非常小心地处理多线程问题

### 异步处理

> 异步处理功能可以节省容器线程。应该将此功能使用在长时间运行的操作上。此功能的作用是释放正在等待完成的线程，使该线程能够被另一请求所使用。

> Servlet或过滤器要支持异步处理，可以通过调用ServletRequest的startAsync方法来启动一个新线程，此方法返回一个AsyncContext的实例，这个实例提供各种方法并且包含ServletRequest和ServletResponse。

#### 启用方式

1. 方式一：
```java
@WebServlet(asyncSupported=true ...)
@WebFilter(asyncSupported=true ...)
```

2. 方式2：
```xml
<servlet>
<servlet-name>AsyncServlet</servlet-name>
<servlet-class>servlet.MyAsyncServlet</servlet-class>
<async-supported>true</async-supported>
</servlet>
```

#### 编写异步Servlets步骤

1. 调用ServletRequest中的startAsync方法。 该startAsync返一个AsyncContext 。
2. 调用AsyncContext的setTimeout()，传递容器等待任务完成的超时时间的毫秒数。此步骤是可选的，但如果你不设置超时，容器的将使用默认的超时时间。如果任务未能在指定的超时时间内完成，将会抛出一个超时异常。
3. 调用asyncContext.start，传递一个Runnable来执行一个长时间运行的任务.
4. 调用Runnable的asynccontext.complete或asynccontext.dispatch方法来完成任务

#### 基本编程框架

```java
final AsyncContext asyncContext = servletRequest.startAsync();
asyncContext.setTimeout( ... );
asyncContext.start(new Runnable() {
@Override
public void run() {
// long running task
asyncContext.complete() or asyncContext.dispatch()
}
})
```

> 示例： AsyncDispatchServlet

#### 异步监听器

1. 为支持Servlet和过滤器配合执行异步操作， Servlet 3.0还增加了asynclistener接口用于接收异步处理过程中发生事件的通知。
2. 异步监听器不需要使用@WebListener注册，而是由异步Servlet自己调用addListener方法注册

> 示例： AsyncListenerServlet
