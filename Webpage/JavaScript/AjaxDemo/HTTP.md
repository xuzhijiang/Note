#### CORS(Cross-Origin Resource Sharing)

1，CORS是用来做什么的

	是HTML5规范定义的如何跨域访问资源。如果浏览器支持HTML5，那么就可以一劳永逸地使用新的跨域策略：CORS了。

2, 如何理解CORS

Origin表示本域，也就是浏览器当前页面的域。当JavaScript向外域(如sina.com）发起请求后，浏览器收到响应后，首先检查Access-Control-Allow-Origin是否包含本域，如果是，则此次跨域请求成功，如果不是，则请求失败，JavaScript将无法获取到响应的任何数据。

[CORS img](../img/CORS.png)

假设本域是my.com，外域是sina.com，只要响应头Access-Control-Allow-Origin为http://my.com，或者是*，本次请求就可以成功。

可见，跨域能否成功，取决于对方服务器是否愿意给你设置一个正确的Access-Control-Allow-Origin，决定权始终在对方手中。

上面这种跨域请求，称之为“简单请求”。简单请求包括GET、HEAD和POST(POST的Content-Type类型，仅限application/x-www-form-urlencoded、multipart/form-data和text/plain），并且不能出现任何自定义头(例如，X-Custom: 12345），通常能满足90%的需求。

在引用外域资源时，除了JavaScript和CSS外，都要验证CORS。例如，当你引用了某个第三方CDN上的字体文件时：

```css
/* CSS */
@font-face {
  font-family: 'FontAwesome';
  src: url('http://cdn.com/fonts/fontawesome.ttf') format('truetype');
}
```
如果该CDN服务商未正确设置Access-Control-Allow-Origin，那么浏览器无法加载字体资源。

对于PUT、DELETE以及其他类型如application/json的POST请求，在发送AJAX请求之前，浏览器会先发送一个OPTIONS请求(称为preflighted请求）到这个URL上，询问目标服务器是否接受：

```html
OPTIONS /path/to/resource HTTP/1.1
Host: bar.com
Origin: http://my.com
Access-Control-Request-Method: POST
服务器必须响应并明确指出允许的Method：

HTTP/1.1 200 OK
Access-Control-Allow-Origin: http://my.com
Access-Control-Allow-Methods: POST, GET, PUT, OPTIONS
Access-Control-Max-Age: 86400
```

浏览器确认服务器响应的`Access-Control-Allow-Methods`头确实包含将要发送的AJAX请求的Method，才会继续发送AJAX，否则，抛出一个错误。

由于以POST、PUT方式传送JSON格式的数据在REST中很常见，所以要跨域正确处理POST和PUT请求，服务器端必须正确响应OPTIONS请求。

#### Cookie

缺陷：
只要满足cookie的作用路径和域，都会带上cookie信息(携带请求头中的Cookie字段)，所以会产生流量代价，
cookie是明文传递的，所以不secure

常用HTTP方法:

    方法            描述	          是否包含主体
	GET    从服务器端获取一份文档     no
	POST   向服务器发送需要处理的数据  yes
	PUT    将请求的主体部分存储在服务器上 yes
	DELETE 从服务器端删除一份文档        no
	HEAD   只获取服务器端文档的头部     no

常见的Http status code:

    200	  请求成功，一般用于GET POST请求         ok
    301   资源移动，所请求的资源自动到新的URL，浏览器自动跳转到新的URL Moved Permanently
    302 Moved temporarily
    304   你访问的资源未修改，所请求的资源未修改，浏览器读取缓存数据                   Not Modified
    400   请求语法错误，服务器无法理解		Bad Request
    404   未找到资源，可以设置个性的404界面		Not Found
    403 for Access Forbidden
    500	   服务器内部错误	Internal Server error

两个主机拥有相同的protocol，port，host，就是同源(origin),
不满足同源策略就是跨域资源访问cors，现在浏览器已经支持了cors的支持了

### HTTP协议

HTTP在TCP/IP通信协议之上运行。

Content Type – text, html, image, pdf etc. Also known as MIME type(也称为MIME type)

MIME类型或内容类型(MIME Type or Content Type)：如果你观察HTTP MIME Type，
它包含“Content-Type”。 它也被称为MIME类型，服务器将其发送给客户端，让他们知道它发送的数据类型。 
它帮助客户端为用户呈现数据。 一些最常用的mime类型是text/html, 
text/xml, application/xml etc.

如果我们不在URL中提供port，则请求转到协议的默认端口, 端口号0到1023是众所周知的服务的保留端口，
例如80表示HTTP，443表示HTTPS，21表示FTP等。