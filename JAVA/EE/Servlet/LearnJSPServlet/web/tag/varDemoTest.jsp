<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

Today's date:
<br/>
<tags:varDemo>
    In long format: ${longDate}
    <br/>
    In short format: ${shortDate}
</tags:varDemo>

</body>
</html>
