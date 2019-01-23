HttpServletResponse表示HTTP环境中的Servlet响应，可以
通过它向客户端返回相应的数据.

重要方法:

1. void addCookie(Cookie cookie) 给这个响应对象添加一个cookie。
2. void addHeader(String name, String value) 给这个响应对象添加一个header。
3. void sendRedirect(String location) 发送一条响应码，将浏览器跳转到指定的位置