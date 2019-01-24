<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
        <%--useBean将创建一个关联Java对象的脚本变量--%>
        <jsp:useBean id="today" class="java.util.Date"/>
        <%=today%>
</body>
</html>

