<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>hihihihi</h3>
    <%--MyHttpSessionAttributeListener的测试--%>
    <%
        session.setAttribute("user", "zhangsan");
        Thread.sleep(3000);
        session.setAttribute("user", "lisi");
        Thread.sleep(3000);
        session.removeAttribute("user");
    %>
</body>
</html>
