<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload Multiple File Request Page</title>
</head>
<body>

<%-- 需要注意的重要一点是，表单enctype应该是multipart/form-data，
以便Spring Web应用程序知道请求包含需要处理的文件数据。--%>

<%--另请注意，对于多个文件，输入字段中的表单字段“file”和“name”是相同的，因此数据将以数组的形式发送。
 我们将获取输入数组并解析文件数据并将其存储在给定的文件名中。--%>
	<form method="POST" action="uploadMultipleFile" enctype="multipart/form-data">
		File1 to upload: <input type="file" name="file"><br /> 
		Name1: <input type="text" name="name"><br /> <br /> 
		File2 to upload: <input type="file" name="file"><br /> 
		Name2: <input type="text" name="name"><br /> <br />
		<input type="submit" value="Upload"> Press here to upload the file!
	</form>
	
</body>
</html>