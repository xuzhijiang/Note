<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head><title>Including a file</title></head>
<body>
    This is the included content:
    <hr/>
    <%@ include file="copyright.jsp" %>
    <!-- include指令也可以包含静态HTML文件 -->
</body>
</html>