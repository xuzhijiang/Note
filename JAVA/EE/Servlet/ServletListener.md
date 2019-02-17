### Why do we have Servlet Listener?

我们知道使用ServletContext，我们可以创建一个具有所有其他servlet可以访问的应用程序范围的属性，但我们可以仅在deploy descriptor(web.xml）中将ServletContext init参数初始化为String。

如果我们的应用程序是面向数据库的，并且我们想在ServletContext中为数据库连接设置一个属性，该怎么办？如果您的应用程序有一个入口点(用户登录），那么您可以在第一个servlet请求中执行此操作，但如果我们有多个入口点，那么在任何地方执行它将导致大量代码冗余。此外，如果数据库已关闭或未正确配置，我们将无法知道，直到第一个客户端请求到达服务器。为了处理这些场景，servlet API提供了我们可以实现和配置的侦听器接口，
以监听事件并执行某些操作。

事件是某种事情的发生，在Web应用程序世界中，事件可以是应用程序的初始化，销毁应用程序，来自客户端的请求，创建/销毁会话，会话中的属性修改等。

Servlet API提供了不同类型的监听器接口，我们可以在web.xml中实现和配置这些接口，以便在发生特定事件时处理某些事务。例如，在上面的场景中，我们可以为应用程序启动事件创建一个Listener，以读取 context init parameters并创建数据库连接并将其
 设置为context属性以供其他资源使用。

### Servlet Listener Interfaces and Event Objects

Servlet API为不同类型的事件提供不同类型的侦听器。 监听器接口声明方法可以处理一组类似的事件，
例如我们有ServletContext Listener来监听上下文的启动和关闭事件。 侦听器接口中的每个方法都将
Event对象作为输入。 Event对象用作包装器，为侦听器提供特定对象。

2. Servlet API provides following event objects.

3. Servlet Listener Configuration

We can use @WebListener annotation to declare a class as Listener,
however the class should implement one or more of the Listener interfaces.

We can define listener in web.xml as:

```xml
<listener>
    <listener-class>
    com.journaldev.listener.AppContextListener
    </listener-class>
</listener>
```