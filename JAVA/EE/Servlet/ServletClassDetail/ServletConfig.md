#### ServletConfig

1. 当Servlet容器初始化Servlet时， Servlet容器会给Servlet的init方法
传入一个ServletConfig对象.
2. ServletConfig封装了 通过@WebServlet或者部署描述符（即web.xml）
传给Servlet的配置信息。这样传入的每一条信息就叫一个初始参数。一个
初始参数有key和value两个组成部分。可以调用getInitParameter方法
读取这些配置参数值

通常有两种方式配置Servlet：

1. 在web.xml中以XML元素方式进行配置
2. 直接在Servlet中使用注解进行配置
