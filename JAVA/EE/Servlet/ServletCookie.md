> Cookie的一些常见用法是,Some of the common usage of cookies are:

1. Cookie在client-server通信中经常使用的，它不是特定于Java的东西。
2. 使用Cookies进行会话认证(Session authentication)，我们在Servlet Session Tutorial中了解到，HttpSession使用“JSESSIONID”这个cookie来跟踪用户会话。
3. 根据客户的偏好对客户进行个性化响应，例如我们可以在客户端浏览器中将背景颜色设置为cookie，然后使用它来自定义响应背景颜色，图像等。

### Java Servlet中的Cookies

Cookie是server发送到client的文本数据，它将保存在client本地计算机上。 当client向server发送请求时，它会将服务器存储的cookie传递给请求头.

客户端可以向服务器发送多个cookie，除了键值对之外，服务器还在响应头中向客户端发送一些其他数据，它看起来如下所示:

```
Set-Cookie	Counter=7;
Version=1;
Comment="SetCookie Counter";
Domain="localhost";
Max-Age=86400;
Expires=Thu, 15-Aug-2013 20:19:19 GMT;
Path=/cookie/SetCookie

Set-Cookie	Test="Test Cookie7";
Version=1;
Comment="Test Cookie"
```

> 请注意，服务器会为cookie发送一些其他信息，例如 comment, domain, maximum time before cookie expires.(cookie到期前的最长时间),以及Path where browser should send the cookie back in request.`但是当client向浏览器发送cookie时，它只会发送cookie的name和value。`

> Note: Throws:IllegalArgumentException - if the cookie name is null or 
empty or contains any illegal characters (for example, a comma, space, 
or semicolon) or matches a token reserved for use by the cookie protocol
,Cookie的value不能包含空格.

Servlet API通过实现Serializable和Cloneable接口的javax.servlet.http.Cookie类提供cookie支持。

HttpServletRequest getCookies()方法是为了从request中获取Cookie数组，因为没有必要把Cookie添加到request中，没有方法可以设置或添加cookie到request。

提供了HttpServletResponse addCookie(Cookie c）方法来在响应头中附加cookie，
没有用于cookie的getter方法.

Cookie类有一个带有name和value的构造函数，因为它们是cookie的必需参数，所有其他参数都是可选的。

### Some important methods of Cookie class are:

1. getComment(） - 返回描述此cookie用途的注释，在客户端使用。请注意，当客户端在请求标头中发送cookie时，服务器不会收到此信息。我们可以使用setComment(）方法在服务器端设置cookie描述。
2. getDomain(） - 返回cookie的域名。我们可以使用setDomain(）方法设置cookie的域名，
如果设置了域名，则cookie将仅发送给该特定域请求。
3. getMaxAge(） - 以秒为单位返回最大年龄。我们可以使用setMaxAge(）来设置cookie的到期时间。
4. getName(） - 返回cookie的名称，可以在浏览器和服务器端使用。名称没有setter，我们只能通过构造函数设置一次名称。
5. getPath(） - 返回浏览器返回此cookie的服务器上的路径。我们将看到它的示例，其中cookie将仅发送到特定资源。
我们可以使用setPath(）来指示浏览器仅将cookie发送到特定资源。
6. getSecure(） - 如果浏览器仅通过安全协议发送cookie，则返回true;如果浏览器可以使用任何协议发送cookie，
则返回false。我们可以使用setSecure(）方法指示浏览器仅通过安全协议发送cookie。
7. getValue(） - 以String的形式返回cookie的值。还有setValue(）方法来更改cookie的值。
8. getVersion(） - 返回此cookie符合的协议版本。还有一个版本的setter方法。
9. isHttpOnly(） - 检查此Cookie是否已标记为HttpOnly。还有一个setter方法，我们可以使用它来指示客户端仅将其用于HTTP。
