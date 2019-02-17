#### ServletRequest

对于每一个HTTP请求，Servlet容器都会创建一个ServletRequest实例，
并将它传给Servlet的Service方法。
ServletRequest封装了关于这个请求的信息，其中包容一些重要的方法：

1. int getContentLength() 响应数据的大小
2. String getContentType() MIME类型
3. String getProtocol() 返回这个HTTP请求的协议名称和版本。
4. Strring getParameter() 返回HTML表单域(或查询字符串）的值

### ServletRequest interface

ServletRequest接口用于向servlet提供client请求信息。 Servlet容器从client request
创建ServletRequest对象，并将其传递给servlet service(）方法进行处理。

```java
public interface ServletRequest {

    // returns the value of named attribute as Object and null if it’s not present. 
    // We can use getAttributeNames() method to get the enumeration of attribute 
    // names for the request. This interface also provide methods for setting and removing attributes.
    Object getAttribute(String name）
    
    String getParameter(String name) – This method returns the request parameter as 
    String. We can use getParameterNames() method to get the enumeration of parameter names for the request.
    
    String getServerName() – returns the hostname of the server.
    
    int getServerPort() – returns the port number of the server on which it’s listening.
    
}
```

ServletRequest的子接口是HttpServletRequest，它包含一些其他用于会话管理，cookie和请求授权的方法。
public interface HttpServletRequest extends ServletRequest {}