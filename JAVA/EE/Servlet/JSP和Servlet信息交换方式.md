## 四种Java Web状态保持技术

* URL重写
* 隐藏域
* cookies
* HTTP Session

> 示例项目： StateManageDemos

>这四种技术在现代Web开发中非常常用，尤其是后两个，使用得更多。
因此，学习与掌握这四种技术，知道其特性，懂得其原理是非常重要的。

### URL重写

`url?key-1=value-1&key-2=value-2 ... &key-n=value-n`

> URL重写适合于tokens无须在太多URL间传递的情况下。

#### 局限性

1. URL在某些浏览器上最大长度为2000字符.
2. 若要传递值到下一个资源，需要将值插入到链接中，换句话
说，静态页面很难传值.
3. URL重写需要在服务端上完成，所有的链接都必须带值，因此
当一个页面存在很多链接时，处理过程会是一个不小的挑战.
4. 某些字符，例如空格、与和问号等必须用base64编码.
5. 所有的信息都是可见的，某些情况下不合适

access(注意URL的变化): http://localhost:8080/top10

### 隐藏域

1. 使用隐藏域来保持状态类似于URL重写技术，但不是将值附加到URL上，而是放到HTML表单的隐藏域中。
2. 当表单提交时，隐藏域的值也同时提交到服务器端。
3. 隐藏域技术仅当网页有表单时有效
4. 该技术相对于URL重写的优势在于：没有字符数限制，同时无须额外的编码。
但该技术同URL重写一样，不适合跨越多个界面。

access: http://localhost:8080/customer

### Cookie

Cookies是一个很小的信息片段，可自动地在浏览器和Web服务器间交互，因此
cookies可存储在多个页面间传递的信息。 Cookie作为HTTP header的一部
分，其传输由HTTP协议控制。此外，可以控制cookies的有效时间。浏览器通
常支持每个网站设置20个cookies。

> Cookies的问题在于用户可以通过改变其浏览器设置来拒绝接受cookies

> 要使用cookies，需要熟悉javax.servlet.http.Cookie类以及
HttpServletRequest和HttpServlet Response两个接口

可以通过传递name和value两个参数给Cookie类的构造函数来创建一个
cookies： `Cookie cookie = new Cookie(name, value);`

创建完一个Cookie对象后，你可以设置domain、 path和maxAge属性。
其中， maxAge属性决定cookie何时过期

要将cookie发送到浏览器，需要调用HttpServletResponse的add方法：
`httpServletResponse.addCookie(cookie);`

浏览器在访问同一Web服务器时，会将之前收到的cookie一并发送。

> 服务端若要读取浏览器提交的cookie，可以通过HttpServletRequest接
口的getCookies方法，该方法返回一个Cookie数组，若没有cookies则返
回null。你需要遍历整个数组来查询某个特定名称的cookie

```java
Cookie[] cookies = request.getCookies();
Cookie maxRecordsCookie = null;
if (cookies != null) {
	for (Cookie cookie : cookies) {
	if (cookie.getName().equals("maxRecords")) {
		maxRecordsCookie = cookie;
		break;
		}
	}
}
```

目前，还没有类似于getCookieByName这样的方法来帮助简化工作。此外，也没有
一个直接的方法来删除一个cookie，你只能创建一个同名的cookie，并将maxAge
属性设置为0，并添加到HttpServletResponse接口中.

```java
Cookie cookie = new Cookie("userName", "");
cookie.setMaxAge(0);
response.addCookie(cookie)
```

access: 
http://localhost:8080/preference
http://localhost:8080/cookieClass
http://localhost:8080/cookieInfo

### Session

#### HttpSession

一个用户可以有且最多有一个HttpSession，并且不会被其他用户访问到。
HttpSession对象在用户第一次访问网站的时候自动被创建，你可以通过调用
HttpServletRequest的getSession方法获取该对象。可以通过HttpSession的setAttribute方法将值放入HttpSession，放到HttpSession的值不限于String类型，可以是任意实现java.io.Serializable的java对象

```java
void setAttribute(String name, Object value)
```

放入到HttpSession 的值，是存储在内存中的，因此，不要往HttpSession放入太多
对象或大对象。尽管现代的Servlet容器在内存不够用的时候会将保存在HttpSessions
的对象转储到二级存储上，但这样有性能问题，因此小心存储。另外，使用Session会给Web应用的水平伸缩带来麻烦

调用setAttribute方法时，若传入的name参数此前已经使用过，则会用
新值覆盖旧值。

通过调用HttpSession的getAttribute方法可以取回之前放入的对象：
`Object getAttribute(String name);`

HttpSession 还有一个非常有用的方法，名为getAttributeNames，该
方法会返回一个Enumeration 对象来迭代访问保存在HttpSession中的所
有值。`Enumeration<String> getAttributeNames();`

* Servlet容器为每个HttpSession 生成唯一的标识，并将该标识发送
给浏览器，或创建一个名为JSESSIONID的cookie，或者在URL后附加
一个名为jsessionid 的参数。
* 可以通过调用 HttpSession的getId方法来读取该标识。
* 在后续的请求中，浏览器会将标识提交给服务端，这样服务器就可以识
别该请求是由哪个用户发起的。 Servlet容器会自动选择一种方式传递
会话标识，无须开发人员介入。

HttpSession.还定义了一个名为invalidate 的方法。该方法强制会话
过期，并清空其保存的对象。 默认情况下， HttpSession 会在用户不活
动一段时间后自动过期，该时间可以通过部署描述符的 sessiontimeout元素配置

access: http:localhost:8080/products

## 小结

本讲介绍了四种Servlet之间信息交换和状态保存的技术，虽
然所介绍的具体代码未必真的能用到开发中，但其基本原理与
思想在Web开发中广泛应用。

所以，学习的要点是通过示例理解这四种技术的特性，这样，
在后面学习各种现代Web开发框架和工具，涉及到这块的内容，
就不会茫然无措