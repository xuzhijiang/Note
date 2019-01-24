<%--
  Created by IntelliJ IDEA.
  User: JinXuLiang
  Date: 2018/1/4
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee</title>
</head>
<body style="text-align: center">
<h3>accept-language: ${header['accept-language']}</h3>
<h3>
    session id: ${pageContext.session.id}
</h3>
<h3>employee: ${requestScope.employee.name}, ${employee.address.city}</h3>
<h3>
    capital: ${capitals["Canada"]}
</h3>

</body>
</html>
