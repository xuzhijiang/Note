### javax.servlet.http中的主要类型

* javax.servlet.Servlet(接口) <-- javax.servlet.GenericServlet <- HttpServlet
* javax.servlet.ServletRequest(接口)<-HttpServletRequest(接口)<-HttpSession(接口)
* javax.servlet.ServletResponse(接口)<-HttpServletResponse(接口)<-Cookie

1. HttpServlet类继承自javax.servlet.GenericServlet类,
2. 使用HttpServlet时，可以使用分别代表Servlet请求和Servlet响应的
HttpServletRequest和HttpServletResponse对象。
3. HttpServletRequest派生自javax.servlet.ServletRequest，
4. HttpServletResponse派生自javax.servlet.ServletResponse

HttpServlet是一个抽象类，它扩展了GenericServlet，并为创建基于HTTP的Web应用程序提供了基础。 有些方法被子类重写为不同的HTTP方法。

```java
public abstract class HttpServlet extends GenericServlet {
    doGet(), for HTTP GET requests
    doPost(), for HTTP POST requests
    doPut(), for HTTP PUT requests
    doDelete(), for HTTP DELETE requests
};
```

>HttpServlet覆盖GenericServlet中的Service方法，并通过下列签名再
添加了一个Service方法：

```java
protected void service(HttpServletRequest request,HttpServletResponse response)
throws ServletException, java.io.IOException
```

>HttpServlet有两个特性是GenericServlet所不具备的：

1. 在实际开发中不是重写Service方法， 而是重写doGet或者doPost，或者重
写doGet和doPost。在少数情况下，还会重写： doHead、 doPut、 doTrace、
doOptions和doDelete方法.
2. 使用HttpServletRequest和HttpServletResponse，而不是
ServletRequest和ServletResponse，前两者比后两者提供了更多的方法
可供调用
