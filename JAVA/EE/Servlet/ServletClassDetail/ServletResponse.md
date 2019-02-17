#### ServletResponse

>javax.servlet.ServletResponse接口表示一个Servlet响应

在调用Servlet的Service方法前， Servlet容器首先创建一个
ServletResponse，并将它作为第二个参数传给Service方法。
ServletResponse隐藏了向浏览器发送响应的复杂过程。

重要方法:

1. getWriter() 返回了一个可以向客户端发送文本的java.io.PrintWriter

### ServletResponse interface

servlet使用ServletResponse接口向客户端发送响应。 
Servlet容器创建ServletResponse对象并将其传递给servlet service(）方法，
然后使用response object为客户端生成HTML响应。

```java
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
```