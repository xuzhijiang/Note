# 早期没有JSP的时候

>把html语句拿出来在Servlet里拼接好再输出,要想拼接数据并完整输出一个html页面，没个几百上千行out.println()是不可能的.

![](早期的拼接html.jpg)

我们的主要目的就是希望在最终输出的html的代码中动态嵌入后台数据罢了.

# JSP(Java Server Pages-Java服务器页面)

所以诞生了jsp,我们可以直接在JSP文件里写HTML代码,HTML/CSS/JS等的写法和HTML文件中的写法是一模一样的。但它毕竟不是HTML，而且本质差了十万八千里。

当请求JSP时，服务器内部会经历一次动态资源（JSP）到静态资源（HTML）的转化，服务器会帮我们把JSP中的HTML片段和数据拼接成静态资源响应给浏览器。也就是说JSP是运行在服务器端，但最终发给客户端的都已经是转换好的HTML静态页面（在响应体里）。

>即：JSP = HTML + Java片段

所谓的“JSP和HTML相似”只是JSP给我们的表面印象。实际上，JSP和HTML差远了。JSP本质是一个Java类（Servlet），是在服务器混的，只不过它输出结果是HTML。

## 当浏览器请求JSP的时候会发生什么

1. WEB容器接收到以.jsp为扩展名的URL的访问请求时，它将把该请求交给JSP引擎去处理。Tomcat中的JSP引擎就是一个Servlet程序($CATALINA_HOME/conf/web.xml中的org.apache.jasper.servlet.JspServlet)，它负责解释和执行JSP页面。
2. 每个JSP 页面在第一次被访问时，JSP引擎将它翻译成一个Servlet源程序，接着再把这个Servlet源程序编译成class类文件，然后再由WEB容器像调用普通Servlet程序一样的方式来装载和解释执行这个由JSP页面翻译成的Servlet程序。 
3. 例如,如果请求hello.jsp会被JSP引擎转化成一个hello_jsp.java,然后通过jdk编译成hello_jsp.class,这个hello_jsp.java就是一个Servlet
4. 存放位置:Tomcat把为JSP页面创建的Servlet源文件和class类文件放置在“$CATALINA\work\Catalina\<主机名>\<应用程序名>\”目录中，例如hello.jsp,Tomcat将JSP页面翻译成的Servlet的包名为org.apache.jsp.hello_jsp.java

![](pics/jsp_class存放目录.jpg)

![](pics/JSP执行过程.jpg)

>原本，我们需要把美工的HTML代码一行行复制到Servlet中，然后拼接数据，最后向客户端响应拼接好的HTML页面。后来我们嫌麻烦，搞了JSP，就可以不用一行行复制HTML代码了，而是在JSP中直接写HTML代码和Java代码，之后JSP引擎把jsp页面编译成一个Servlet，通过Java代码获取后台数据后拼接到HTML片段中，最终通过out.println()输出。

---

![](pics/jsp响应客户端的过程.jpg)

![](pics/jsp转换后的java文件.jpg)

    也就是说，虽然我们不用像上古时期一样手动拼接html语句到Servlet了，
    但是JSP转换后的Java类其实还是在out.println()输出。和我们手动拼接是一样的结果。

    而index_jsp.java这个类继承了HttpJspBase，而HttpJspBase间接实现了Servlet接口。
    所以可以说，index.jsp被翻译后的Java类也是一个Servlet，所以JSP本质也是一个Servlet。

    JSP的EL表达式是在服务器端起作用,而不是浏览器端.
---

# JSP与AJAX+HTML

其实请求、响应这么一来一回，无非要的就两样东西：数据+HTML骨架。

1. 服务器端可以组装好数据再发给浏览器(JSP)
2. 先发送html骨架,然后浏览器通过ajax请求自己组装数据(ajax+html)

>JSP是服务器端的，它的局限性在于数据必须在返回给客户端之前就“装载”完毕。不然HTML都已经发出去了，你就没办法跑到浏览器里把数据给它安上。而有了AJAX，就可以实现先发送html骨架、然后在浏览器组装了。

![](pics/ajax_html.jpg)

>初学者朋友是不是听说JSP被淘汰了，犹豫要不要学？别闹了，不学JSP你都没法学其他的模板技术。而且，你可能觉得很新奇的freemarker，其实学过JSP的话，要上手只要几小时。

再强调一点，虽然我们在浏览器地址栏输入localhost:8080/xxx/xxx.jsp，就显示出了当前页面，但那不是JSP页面，而是HTML页面。服务器并没有直接把JSP文件从服务端扔到客户端！JSP是Java Server Page，是服务器端的东西。服务器的东西永远不可能直接在浏览器运行。浏览器只能接受静态页面。

# MVC模式与JAVAEE三层架构

![](pics/JavaEE三层架构.jpg)

MVC模式不是是JavaEE独有的。MVC是web开发都有的一种模式，比如PHP开发web时也有MVC模式。而JavaEE三层架构则是JavaEE的：Controller/Service/Dao。分层开发是为了使代码逻辑更加清晰，也起到了一定的解耦合作用。

![](pics/MVC模式.jpg)
