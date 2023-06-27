
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--导入自定义函数库--%>
<%@ taglib uri="/WEB-INF/functiontags.tld" prefix="f"%>
<html>
<head>
    <title>Testing reverseString function</title>
</head>
<body style="text-align: center">
    <%--使用自定义函数--%>
    <h2>${f:reverseString("Hello World")}</h2>
</body>
</html>
