<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--在此指明本页用于显示出错信息--%>
<%@page isErrorPage="true"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
        An error has occurred. <br/>
        Error message:
        <%
            out.println(exception.toString());
        %>
</body>
</html>

