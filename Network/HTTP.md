# 浏览器缓存机制剖析

缓存的种类很多：浏览器缓存、CDN 缓存、代理服务器缓存等。

![](https://ask.qcloudimg.com/http-save/yehe-1263954/ws4qyjomh4.jpeg?imageView2/2/w/1620)

浏览器的缓存机制分为两个部分：

1. 当前缓存是否过期？
2. 服务器中的文件是否有改动？

- 第一步：如果浏览器判断这个缓存没有过期可以用，那么连请求都不会发的，直接是启用之前浏览器缓存下来的那份文件，此时状态码为200
- 第二步: 如果缓存过期,则判断服务器中的文件是否有改动

1. 缓存过期，文件有改动，那么下载新文件，此时状态码为200
2. 缓存过期，文件无改动，那么服务器只会给你返回一个头信息(304)，浏览器读取304后，就会去读取过期缓存文件。

## 如何判断缓存是否过期以及文件是否有改动？

存储策略, 过期策略, 协商策略,  其中存储策略在收到响应后应用(存储缓存), 过期策略(判断是否过期), 协商策略(如果过期了,是否需要重新下载)在发送请求前应用. 流程图如下所示.

![](https://ask.qcloudimg.com/http-save/yehe-1263954/s91dv6ipqc.jpeg?imageView2/2/w/1620)

## Pragma

http1.0字段, 通常设置为Pragma:no-cache, 作用同Cache-Control:no-cache.

## Cache-Control(http1.1,解决1.0中expires的问题)

Cache-Control是金字塔顶尖的规则, 它藐视一切其他设置, 只要其他设置与其抵触, 一律覆盖之.

语法为: “Cache-Control : value", value有12种,下面是常用的:

- public: 资源将被客户端和代理服务器,CDN等缓存
- private: 资源仅被客户端缓存, 代理服务器不缓存
- no-store: 不进行任何缓存
- no-cache: 相当于max-age:0,must-revalidate即资源被缓存, 但是缓存立刻过期, 同时下次访问时强制验证资源有效性,缓存服务器需要先向源服务器验证缓存资源的有效性.
- max-age:缓存资源, 但是在指定时间(单位为秒)后缓存过期          
- s-maxage: 同上, 依赖public设置, 覆盖max-age, 且只在代理服务器上有效（比如CDN缓存）.max-age用于普通缓存，而s-maxage用于代理缓存。
- must-revalidation: 如果缓存失效, 强制重新向服务器(或代理)发起验证

public：所有内容都将被缓存（客户端和代理服务器都可缓存）。具体来说响应可被任何中间节点缓存，如 Browser <-- proxy1 <--  proxy2 <-- Server，中间的proxy可以缓存资源，比如下次再请求同一资源proxy1直接把自己缓存的东西给 Browser 而不再向proxy2要。

private：所有内容只有客户端可以缓存，Cache-Control的默认取值。具体来说，表示中间节点不允许缓存，对于Browser <-- proxy1 <--  proxy2 <-- Server，proxy 会老老实实把Server 返回的数据发送给proxy1,自己不缓存任何数据。当下次Browser再次请求时proxy会做好请求转发而不是自作主张给自己缓存的数据。

## Expires(GMT格式,http1.0)

即到期时间, 以服务器时间为参考系, 其优先级比 Cache-Control:max-age 低, 两者同时出现在响应头时, Expires将被后者覆盖.只有 Cache-Control生效. 如果Expires, Cache-Control: max-age, 或 Cache-Control:s-maxage 都没有在响应头中出现, 并且也没有其它缓存的设置, 那么浏览器默认会采用一个启发式的算法, 通常会取响应头的Date_value - Last-Modified_value值的10%作为缓存时间: `其缓存时间为 (Date_value - Last-Modified_value) * 10%`

对于常规资源, 建议明确设置缓存时间(如指定max-age 或 expires).

## Expires VS. max-age

Expires和max-age都是用于控制缓存的生存时间。不同的是Expires指定的是过期的具体时间,而max-age指定的是生命时长的秒数.

区别在于Expires是 HTTP/1.0 的中的标准，而max-age是属于Cache-Control的内容，是 HTTP/1.1 中的定义的。但为了想向前兼容，这两个属性仍然要同时存在。

更倾向于使用max-age,Expires依赖于时区,如果时区设置错误,那么缓存将出现问题.

## 判断文件变动

常用的方式为Etag和Last-Modified,

Last-Modified方式需要用到两个字段：Last-Modified & if-modified-since。格式是GMT,

![](https://ask.qcloudimg.com/http-save/yehe-1263954/04782tl9ah.jpeg?imageView2/2/w/1620)

当第一次请求某一个文件的时候，就会传递回来一个Last-Modified 字段，其内容是这个文件的修改时间。当这个文件缓存过期，浏览器又向服务器请求这个文件的时候，会自动带一个请求头字段If-Modified-Since，其值是上一次传递过来的Last-Modified的值，拿这个值去和服务器中现在这个文件的最后修改时间做对比，如果相等，那么就不会重新拉取这个文件了，返回304让浏览器读过期缓存。如果不相等就重新拉取,返回200.

Last-Modified是 ETag 的fallback机制, 优先级比 ETag 低, 且只能精确到秒, 因此不太适合短时间内频繁改动的资源. 不仅如此, 服务器端的静态资源, 通常需要编译打包, 可能出现资源内容没有改变,而Last-Modified却改变的情况。

## ETag

服务器资源的唯一标识符, 浏览器可以根据ETag值缓存数据,ETag 优先级比 Last-Modified 高.

## If-None-Match

缓存校验字段, 结合ETag字段, 常用于判断缓存资源是否有效, 优先级比If-Modified-Since高.

对于 GET 或 HEAD 请求, 如果其etags列表均不匹配, 服务器将返回200状态码的响应, 反之, 将返回304(Not Modified)状态码的响应. 

## 强缓存(对应缓存没有过期)

一旦资源命中强缓存, 浏览器便不会向服务器发送请求, 而是直接读取缓存. Chrome下的现象是 200 OK (from disk cache) 或者 200 OK (from memory cache). 

只要存在缓存, 且Cache-Control:max-age 或者expires没有过期, 那么就能命中强缓存.

## 协商缓存(对应缓存过期,需要协商是否需要重新拉取)

缓存过期后, 继续请求该资源: 

- 根据上次响应中的ETag_value, 自动往request header中添加If-None-Match字段. 服务器收到请求后,                    拿If-None-Match字段的值与资源的ETag值进行比较, 若相同, 则命中协商缓存, 返回304响应.
- 根据上次响应中的Last-Modified_value, 自动往request header中添加If-Modified-Since字段. 服务器收到请求后, 拿If-Modified-Since字段的值与资源的Last-Modified值进行比较, 若相同, 则命中协商缓存, 返回304响应.

以上, ETag优先级比Last-Modified高, 同时存在时, 前者覆盖后者. 协商缓存的响应结果, 不仅验证了资源的有效性, 同时还更新了浏览器缓存. 主要更新内容如下:

>Age:0 Cache-Control:max-age=600 Date: Wed, 05 Apr 2017 13:09:36 GMT Expires:Wed, 05 Apr 2017 00:55:35 GMT1234

Age:0 表示命中了代理服务器的缓存, age值为0表示代理服务器刚刚刷新了一次缓存.age表示代理服务器从刷新缓存到现在的时间.

Cache-Control:max-age=600 覆盖 Expires 字段.

## Age

出现此字段, 表示命中代理服务器的缓存. 它指的是代理服务器对于请求资源的已缓存时间, 单位为秒. 如下:

>Age:2383321 Date:Wed, 08 Mar 2017 16:12:42 GMT12

以上指的是, 代理服务器在2017年3月8日16:12:42时向源服务器发起了对该资源的请求, 目前已缓存了该资源2383321秒

## Date

指的是响应生成的时间. 请求经过代理服务器时, 返回的Date未必是最新的, 通常这个时候, 代理服务器将增加一个Age字段告知该资源已缓存了多久.

## Vary

对于服务器而言, 资源文件可能不止一个版本, 比如说压缩和未压缩, 针对不同的客户端, 通常需要返回不同的资源版本. 比如说老式的浏览器可能不支持解压缩, 这个时候, 就需要返回一个未压缩的版本; 对于新的浏览器,            支持压缩, 返回一个压缩的版本, 有利于节省带宽, 提升体验. 那么怎么区分这个版本呢, 这个时候就需要Vary了.

服务器通过指定Vary: Accept-Encoding, 告知代理服务器, 对于这个资源, 需要缓存两个版本: 压缩和未压缩. 这样老式浏览器和新的浏览器, 通过代理,就分别拿到了未压缩和压缩版本的资源, 避免了都拿同一个资源的尴尬.

>Vary:Accept-Encoding,User-Agent1

如上设置, 代理服务器将针对是否压缩和浏览器类型两个维度去缓存资源. 如此一来, 同一个url, 就能针对PC和Mobile返回不同的缓存内容。

## 怎么让浏览器不缓存静态资源

实际上, 工作中很多场景都需要避免浏览器缓存, 请求时想要禁用缓存, 还可以设置请求头: Cache-Control: no-cache, no-store, must-revalidate .当然, 还有一种常用做法: 即给请求的资源增加一个版本号, 如下:

<link rel="stylesheet" type="text/css" href="../css/style.css?version=1.8.9"/>

这样做的好处就是你可以自由控制什么时候加载最新的资源.

不仅如此, HTML也可以禁用缓存, 即在页面的meta设置

><meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>

上述虽能禁用缓存, 但只有部分浏览器支持, 而且由于代理不解析HTML文档, 故代理服务器也不支持这种方式.

# 实际场景应用缓存策略

1. 频繁变动的资源: Cache-Control: no-cache

对于频繁变动的资源，首先需要使用Cache-Control: no-cache 使浏览器每次都请求服务器，然后配合 ETag 或者 Last-Modified 来验证资源是否有效。这样的做法虽然不能节省请求数量，但是能显著减少响应数据大小。

2. 不常变化的资源: Cache-Control: max-age=31536000

通常在处理这类资源时，给它们的 Cache-Control 配置一个很大的 max-age=31536000 (一年)，这样浏览器之后请求相同的 URL 会命中强制缓存。而为了解决更新的问题，就需要在文件名(或者路径)中添加 hash， 版本号等动态字符，之后更改动态字符，从而达到更改引用 URL 的目的，让之前的强制缓存失效 (其实并未立即失效，只是不再使用了而已)。
在线提供的类库 (如 jquery-3.3.1.min.js, lodash.min.js 等) 均采用这个模式。

## 移动端的缓存处理

移动端，任何一个网络请求的增加，对于移动端的加载消耗时间都是比较大的。那么，上述的缓存有什么问题呢？其实，强制缓存是没有太大问题的，因为只要缓存不到期，是不会想服务器发送请求的；但是如果是对比缓存的情况下，304的问题就比较巨大，因为它会造成无用的请求。每次在使用缓存前，都会向服务器发送请求确认，导致网络的延时。

一次完美的缓存必须保证两点：

1. 数据缓存之后，尽量减少服务器的请求
2. 如果资源更新的话，必须使得客户端的资源一起更新。

所以，一般我们会运用的方式是：在资源文件后面加上表示，如config.f1ec3.js、config.v1.js之类的，然后给资源设置较长的缓存时间，如一年: Cache-Control: max-age=31536000, 这样子，就不会造成304的回包现象。然后一旦资源发生更新时，我们可以改变资源后面的标识符，实现静态资源非覆盖式更新。

![参考](https://cloud.tencent.com/developer/article/1169232)

# CORS(Cross-Origin Resource Sharing)

两个主机拥有相同的protocol，port，host，就是同源(origin),不满足同源策略就是跨域资源访问cors，现在浏览器已经支持了cors的支持了

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

---

    注意,这部分已经作废,看Network下的http协议.md

# Cookie

缺陷：只要满足cookie的作用路径和域，都会带上cookie信息(携带请求头中的Cookie字段)，所以会产生流量代价，
cookie是明文传递的，所以不secure
    
## 常用HTTP方法:

    方法            描述	          是否包含主体
	GET    从服务器端获取一份文档     no
	POST   向服务器发送需要处理的数据  yes
	PUT    将请求的主体部分存储在服务器上 yes
	DELETE 从服务器端删除一份文档        no
	HEAD   只获取服务器端文档的头部     no

>所有状态码的第一个数字代表了响应的五种状态之一：

2xx : 处理成功响应
3xx : 重定向响应类
4xx : 客户端错误
5xx : 服务端错误

200	  请求成功.
304   你访问的资源未修改，所请求的资源未修改，浏览器读取缓存数据                   Not Modified
400   请求语法错误，服务器无法理解		Bad Request
404   未找到资源，可以设置个性的404界面		Not Found
403   Access Forbidden,请求不允许
500	  服务器内部错误	Internal Server error

### HTTP协议

HTTP在TCP/IP通信协议之上运行。

Content Type – text, html, image, pdf etc. Also known as MIME type(也称为MIME type)

MIME类型或内容类型(MIME Type or Content Type)：如果你观察HTTP MIME Type，
它包含“Content-Type”。 它也被称为MIME类型，服务器将其发送给客户端，让他们知道它发送的数据类型。 
它帮助客户端为用户呈现数据。 一些最常用的mime类型是text/html, 
text/xml, application/xml etc.

如果我们不在URL中提供port，则请求转到协议的默认端口, 端口号0到1023是众所周知的服务的保留端口，
例如80表示HTTP，443表示HTTPS，21表示FTP等。

        注意,这部分已经作废,看Network下的http协议.md

----
