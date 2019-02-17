### RequestDispatcher interface

RequestDispatcher接口用于将请求转发到另一个资源，该资源可以是同一上下文中的HTML，
JSP或其他servlet。 我们还可以使用它将另一个资源的内容包含在响应中。 
此接口用于同一上下文中的servlet通信。

RequestDispatcher interface is used to forward the request to another 
resource that can be HTML, JSP or another servlet in the same context.
 We can also use this to include the content of another resource to the 
 response. This interface is used for servlet communication within the 
 same context.
 
我们可以使用ServletContext getRequestDispatcher(String path）方法在servlet中
获取RequestDispatcher。 路径必须以/开头，并且被解释为相对于当前上下文根。

```java
public interface RequestDispatcher {
    void forward(ServletRequest请求，ServletResponse响应） - 将请求从servlet转发到服务器上的另一个资源(servlet，JSP文件或HTML文件）。
    
    void include(ServletRequest请求，ServletResponse响应） - 包括响应中的资源(servlet，JSP页面，HTML文件）的内容。
}
```