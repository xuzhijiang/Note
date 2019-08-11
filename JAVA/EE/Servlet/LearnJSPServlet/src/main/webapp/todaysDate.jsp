<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <%@...%>块被称为"页面指令" -->
<html>
<head>
    <title>Today's date</title>
</head>
<body>
<%
    DateFormat dateFormat =
            DateFormat.getDateInstance(DateFormat.LONG);
    String s = dateFormat.format(new Date());
    out.println("Today is " + s);
%>
<!-- <%...%>块被称为"scriplet" -->
</body>
</html>
