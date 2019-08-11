<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--如果出错，由errorHandler.jsp负责显示出错信息--%>
<%@page errorPage="errorHandler.jsp"%>
<!-- 捕获并显示异常信息 -->
<html>
<head>
    <title>Title</title>
</head>
<body>
        Deliberately throw an exception
        <%
            //将引发未捕获的异常
            Integer.parseInt("Throw me");
        %>
</body>
</html>

<!-- http://localhost:8080/buggy.jsp -->
