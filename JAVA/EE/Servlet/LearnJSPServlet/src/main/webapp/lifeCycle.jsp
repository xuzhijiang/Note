<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%!
        public void jspInit() {
            System.out.println("jspInit ...");
        }
        public void jspDestroy() {
            System.out.println("jspDestroy ...");
        }
    %>
    <!-- 当Servlet/JSP容器关闭时，jspDestory方法被调用，可以在tomcat命令行看到打印 -->
    <h2>Overriding jspInit and jspDestroy</h2>
</body>
</html>
