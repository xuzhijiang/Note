<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--导入自定义标签库--%>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="easy"%>
<html>
<head>
    <title>Testing DataFormatterTag</title>
</head>
<body style="text-align: center">
        <%--第一种方式：使用attribute指定自定义标签的属性值--%>
        <easy:dataFormatter header="States"
                            items="Alabama,Alaska,Georgia,Florida"
        />
        <br/>
        <%--另外一种使用方式：使用子元素--%>
        <easy:dataFormatter header="Countries">
        <jsp:attribute name="items">
                US,UK,Canada,Korea
        </jsp:attribute>
        </easy:dataFormatter>
</body>
</html>
