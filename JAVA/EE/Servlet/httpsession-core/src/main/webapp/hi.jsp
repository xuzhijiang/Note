<%@ page import="com.session.core.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%--测试绑定,解绑,活化,钝化--%>
    <h1>绑定,解绑,活化,钝化</h1>
    <%
        User user = new User();
        session.setAttribute("uuu", user);
        Thread.sleep(3000);
        session.removeAttribute("uuu");
    %>
</body>
</html>
