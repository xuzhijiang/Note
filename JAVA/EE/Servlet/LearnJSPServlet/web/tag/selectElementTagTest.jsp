
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="easy"%>
<html>
<head>
    <title>Testing SelectElementFormatterTag</title>
</head>
<body style="text-align: center">
    <easy:select>
        <option value="${value}">${text}</option>
    </easy:select>
</body>
</html>
