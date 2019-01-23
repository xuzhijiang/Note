封装了HTTP请求相关的信息

重要方法:

1. String getContextPath() 返回表示请求上下文的请求URI部分。
2. Cookie[] getCookies() 返回一个Cookie对象数组
3. String getHeader(String name) 返回指定HTTP首部的值
4. String getMethod() 返回生成这个请求的HTTP方法名称
5. String getQueryString() 返回请求URL中的查询字符串。
6. HttpSession getSession() 返回与这个请求相关的会话对象。如果没有，
将创建一个新的会话对象。