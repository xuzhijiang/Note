# JSP中9大隐式对象

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

# 内置对象示例

    access: http://localhost:8080/implicitObjects.jsp
    示例中用到的内置对象: request, response, session, config, application

# 页面指令

```jsp
<%@ page attribute1="value1" attribute2="value2" ... %>
```

    指令属性名 说明
    import    定义一个或多个本页面中将被导入和使用的java类型。
    session   值为True，本页面加入会话管理；值为False则相反。
    buffer    以KB为单位，定义隐式对象out的缓冲大小。必须以KB后缀结尾。默认大小为8KB或更大(取决于JSP容器）。该值可以为none，这意味着没有缓冲，所有数据将直接写入PrintWriter。
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

# include指令

    使用include指令将其他文件中的内容包含到当前JSP页面

```jsp
<%@ include file="url"%>
```

    include指令也可以包含静态HTML文件

### 表达式

每个表达式都会被JSP容器执行，并使用隐式对象out的打印方法输出结果。表
达式以“<%=”开始， 并以“%>”结束.

Today is <%=java.util.Calendar.getInstance().getTime()%> 等价于<=>
Today is <% out.print(java.util.Calendar.getInstance().getTime()); %>

* Java代码可以出现在JSP页面中的任何位置，并通过“<%”和“%>”包围。
* 可以使用page指令的import属性导入在JSP页面中使用的Java类型，如果没
有导入的类型，必须在代码中写Java类的全路径名称.

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

# JSP标准标签库

JSP标准标签库(JavaServer Pages Standard Tag Library， JSTL)是一个定制标签库的集合，用来解决像遍历Map或集合、条件测试、 XML处理，
甚至数据库访问和数据操作等常见的问题.

JSTL目前的最新版本是1.2，这是由JSR-52专家组在JCP(www.jcp.org）上
定义的，在java.net网站上可以下载：http://jstl.java.net
其中， “JSTL API”和“JSTL实现”这两个软件是必需下载的。

1. JSTL API，包含javax.servlet.jsp.jstl包，里面包含了JSTL规范中定
义的类型
2. JSTL实现，包含实现类。这两个JAR文件都必须复制到应用JSTL的每个应用
程序的WEB-INF/lib目录下

### JSTL标签库

区域      uri                                     前缀
核心      http://java.sun.com/jsp/jstl/core        c
XML       http://java.sun.com/jsp/jstl/xml         x
国际化    http://java.sun.com/jsp/jstl/fmt         fmt 
数据库    http://java.sun.com/jsp/jstl/sql          sql
函数      http://java.sun.com/jsp/jstl/functions    fn

#### 使用方法

在JSP页面中使用JSTL库，必须通过以下格式使用taglib指令：`<%@ taglib uri="uri" prefix="prefix" %>`, 例如，要使用Core库，必须在JSP页面的开头处做以下声明：
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

#### out标签

```jsp
<c:out value="value"/>
<c:out />
```

#### set标签

>利用set标签，可以完成以下工作：

1. 创建一个字符串和一个引用该字符串的有界变量。
2. 创建一个引用现存有界对象的有界变量。
3. 设置有界对象的属性。

>如果用set创建有界变量，那么，在该标签出现后的整个JSP页面中都可以使
用该变量

```jsp
<c:set value="value" var="varName"
[scope="{page|request|session|application}"]/>

<c:set var="varName" [scope="{page|request|session|application}"]>
body content
</c:set>
```

>允许在body content中有JSP代码

```jsp
<c:set target="target" property="propertyName" value="value"/>

<c:set target="target" property="propertyName">
body content
</c:set>
```

#### remove标签

> remove标签用于删除有界变量

```jsp
<c:remove var="varName" [scope="{page|request|session|application}"]/>
```

#### if标签

>语法：

```jsp
<c:if test="testCondition" var="varName"
[scope="{page|request|session|application}"]/>

<c:if test="testCondition [var="varName"]
[scope="{page|request|session|application}"]>
body content
</c:if>
```

#### choose、 when和otherwise标签

```jsp
<c:choose></c:choose>
```

#### forEach标签

>语法：

```jsp
<c:forEach [var="varName"] begin="begin" end="end" step="step">
body content
</c:forEach>

<c:forEach items="collection" [var="varName"]
[varStatus="varStatusName"] [begin="begin"] [end="end"]
[step="step"]>
body content
</c:forEach>
```

forEach标签有一个类型为javax.servlet.jsp.jstl.core.LoopTagStatus的变量varStatus。 LoopTagStatus接口带有count属性，它返回当前遍历的“次数”。

access: http://localhost:8080/books

##### forEach标签示例

access: http://localhost:8080/bigCities

#### forTokens标签

>forTokens标签用于遍历以特定分隔符隔开的令牌：

```jsp
<c:forTokens items="stringOfTokens" delims="delimiters"
[var="varName"] [varStatus="varStatusName"] [begin="begin"] [end="end"] [step="step"]>
body content
</c:forTokens>
```

#### formatNumber标签

```jsp
<fmt:formatNumber [type="{number|currency|percent}"]
[pattern="customPattern"]
[currencyCode="currencyCode"]
[currencySymbol="currencySymbol"]
[groupingUsed="{true|false}"]
[maxIntegerDigits="maxIntegerDigits"]
[minIntegerDigits="minIntegerDigits"]
[maxFractionDigits="maxFractionDigits"]
[minFractionDigits="minFractionDigits"]
[var="varName"]
[scope="{page|request|session|application}"]>
numeric value to be formatted
</fmt:formatNumber>
```

#### formatDate标签

```jsp
<fmt:formatDate value="date"
[type="{time|date|both}"]
[dateStyle="{default|short|medium|long|full}"]
[timeStyle="{default|short|medium|long|full}"]
[pattern="customPattern"]
[timeZone="timeZone"]
[var="varName"]
[scope="{page|request|session|application}"]
/>
```

#### parseNumber标签

>parseNumber标签用于将以字符串表示的数字、 货币或者百分比解析成数字

```jsp
<fmt:parseNumber [type="{number|currency|percent}"]
[pattern="customPattern"]
[parseLocale="parseLocale"]
[integerOnly="{true|false}"]
[var="varName"]
[scope="{page|request|session|application}"]>
numeric value to be parsed
</fmt:parseNumber>
```

#### parseDate标签

```jsp
<fmt:parseDate [type="{time|date|both}"]
[dateStyle="{default|short|medium|long|full}"]
[timeStyle="{default|short|medium|long|full}"]
[pattern="customPattern"]
[timeZone="timeZone"]
[parseLocale="parseLocale"]
[var="varName"]
[scope="{page|request|session|application}"]>
date value to be parsed
</fmt:parseDate>
```

## 函数

* 除了定制行为外， JSTL 1.1和JSTL 1.2还定义了一套可以在EL表达式中使用的标准函数
* 这些函数都集中放在function标签库中。为了使用这些函数，必须在JSP的最前面使用以下taglib指令：<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
prefix="fn" %>
* 调用函数时，要以下列格式使用一个EL：${fn:functionName}

### contains函数

* contains函数用于测试一个字符串中是否包含指定的子字符串。如果字符串中包含该子字符串，则返回值为True，否则，返回False

* 类似地，还有containsIgnoreCase、 startsWith/endsWith，indexOf， join,length,replace,split,substring,substringAfter/substringbefore,toLowerCase/toUpperCase,trim等字符串函数，用法都大同小异

### escapeXml函数

escapeXml函数用于给String编码。这种转换与out标签将其escapeXml属性设为True一样。
例如，下面的EL表达式：

> ${fn:escapeXml("Use <br/> to change lines")}

将被渲染成：`Use &lt;br/&gt; to change lines`

## 小结

> JSP有着悠久的历史，并且在早期的Web开发中得到了广泛的应用。
但随着时代的进步，直接用它来开发的网站也越来越少，属于一种“维护状态”

> 现在学习JSP的主要目的是为了维护老的JSP代码，所以有所了解就行了，没必要在上面花太多的时间

> 本讲基本上涵盖了JSP的主要技术点，配合着相应的示例，学完之后，看懂现有的“老” JSP应该问题不大。