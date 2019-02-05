JSP页面本质上是一个Servlet，在JSP容器中运行，一个Servlet容器通
常也是JSP容器，当一个JSP页面第一次被请求时， Servlet/JSP容器主要
做以下两件事情：

1. 转换JSP页面到JSP页面实现类，该实现类是一个实现javax.servlet.jsp.JspPage接口或实现
javax.servlet.jsp.HttpJspPage子接口的Java类。 JspPage是javax.servlet.Servlet的子接口，这使得每一个JSP页面都是一个
Servlet。该实现类的类名由Servlet/JSP容器生成。
2. 如果转换成功， Servlet/JSP容器随后编译该Servlet类，并装载和
实例化该类，像其他正常的Servlet一样执行生命周期操作

* Java代码可以出现在JSP页面中的任何位置，并通过“<%”和“%>”包围。
* 可以使用page指令的import属性导入在JSP页面中使用的Java类型，如果没
有导入的类型，必须在代码中写Java类的全路径名称.

### JSP隐式对象

隐式对象指那些由系统提供，可以在JSP页面直接使用的对象：

对象          所属类型
request     javax.servlet.http.HttpServletRequest
response    javax.servlet.http.HttpServletResponse
out         javax.servlet.jsp.JspWriter
session     javax.servlet.http.HttpSession
application javax.servlet.ServletContext
config      javax.servlet.ServletConfig
pageContext javax.servlet.jsp.PageContext
page        javax.servlet.jsp.HttpJspPage
exception   java.lang.Throwable

### 保存数据的“范围”

PageContext对象提供了一个有趣的方法：

`public abstract void setAttribute(String name, Object value, int scope)`

有4种scope取值：

* PAGE_SCOPE
* REQUEST_SCOPE
* SESSION_SCOPE
* APPLICATION_SCOPE

```jsp
<%
//product is a Java object
pageContext.setAttribute("product", product,PageContext.REQUEST_SCOPE);
%>
```

### 内置对象示例

access: http://localhost:8080/implicitObjects.jsp

示例中用到的内置对象: request, response, session, config, application

### 设置输出编码

在默认情况下，JSP编译器会将JSP页面的内容类型设为text/html。如果要使
用不同的类型，则需要通过调用response.setContentType()或者使用页面指
令来设置内容类型.

`response.setContentType("text/json");`

### 页面指令

```jsp
<%@ page attribute1="value1" attribute2="value2" ... %>
```

指令属性名 说明
import    定义一个或多个本页面中将被导入和使用的java类型。
session   值为True，本页面加入会话管理；值为False则相反。
buffer    以KB为单位，定义隐式对象out的缓冲大小。必须以KB后缀结尾。默认大小为8KB或更大（取决于JSP容器）。该值可以为none，这意味着没有缓冲，所有数据将直接写入PrintWriter。
autoFlush 默认值为True。若值为True，则当输出缓冲满时会自写入输出流。而值为False，则仅当调用隐式对象的flush方法时，才会写入输出流。因此，若缓冲溢出，则会抛出异常。
isThreadSafe 定义该页面的线程安全级别
info      返回调用容器生成的Servlet类的getServletInfo方法的结果

指令属性名 说明
errorPage    定义当出错时用来处理错误的页面。
isErrorPage  标识本页是一个错误处理页面。
contentType  定义本页面隐式对象response的内容类型，默认是text/html。
pageEncoding 定义本页面的字符编码，默认是ISO-8859-1
isELIgnored  配置是否忽略EL表达式。 EL是Expression Language的缩写。
language     定义本页面的脚本语言类型，默认是Java。
Extends      定义JSP实现类要继承的父类。

### include指令

使用include指令将其他文件中的内容包含到当前JSP页面。一个页面中可以
有多个include指令。若存在一个内容会在多个不同页面中使用或一个页面不
同位置使用的场景，则将该内容模块化到一个include文件非常有用。

```jsp
<%@ include file="url"%>
```

JSP转换器处理include指令时，将指令替换为指令所包含文件的内容。

#### include指令的用法

>include指令也可以包含静态HTML文件

access: http://localhost:8080/main.jsp

### 表达式

每个表达式都会被JSP容器执行，并使用隐式对象out的打印方法输出结果。表
达式以“<%=”开始， 并以“%>”结束.

Today is <%=java.util.Calendar.getInstance().getTime()%> 等价于<=>
Today is <% out.print(java.util.Calendar.getInstance().getTime()); %>

### 声明

可以声明能在JSP页面中使用的变量和方法。 声明以“<%!”开始，
并以“%>”结束。

>在JSP页面中，一个声明可以出现在任何地方，并且一个页面可以有多个声明.

access: http://localhost:8080/declarationTest.jsp

可以使用声明来重写JSP页面，实现类的init和destroy方法。

1. 通过声明jspInit方法，来重写init方法。
2. 通过声明jspDestroy方法，来重写destory方法。

* jspInit(): JSP页面在初始化时调用jspInit,此没有参数，但可以通过隐式对象config访问ServletConfig对象
* jspDestroy(): 在JSP页面将被销毁时调用，用得不多

access: http://localhost:8080/lifeCycle.jsp

### Action(动作)

JSP页面中，动作被转换成Java代码来执行操作，如访问一个Java对象或调用方
法.

access: http://localhost:8080/useBeanTest.jsp

### 设置与读取JavaBean属性值的Action

access: http://localhost:8080/getSetPropertyTest.jsp

### "include"动作

include动作用来动态地引入另一个资源。可以引入另一个JSP页面，
也可以引入一个Servlet或一个静态的HTML页面。

```jsp
<jsp:include page="jspf/menu.jsp">
    <jsp:param name="text" value="How are you?"/>
</jsp:include>
```

### 区分inclue页面指令与include Action

1. 第一个不同是: 对于include指令，资源引入发生在页面转换时，即当JSP容器
将`页面`转换为`生成的Servlet`时。而对于include动作，资源引
入发生在请求页面时。因此，使用include动作是可以传递参数
的，而include指令不支持。
2. 第二个不同是: include指令对引入的文件扩展名不做特殊要求。
但对于include动作，若引入的文件需以JSP页面处理，则其文
件扩展名必须是JSP。

### forward动作

forward将当前页面转向到其他资源.

```jsp
<jsp:forward page="jspf/login.jsp">
<jsp:param name="text" value="Please login"/>
</jsp:forward>
```

