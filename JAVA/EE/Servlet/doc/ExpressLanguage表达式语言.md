### Expression Language(表达式语言):EL

* EL表达式以“${”开头，并以“}”结束: ${expression}.
* 如果需要的只是文本“${”， 则需要在它前面加一个转义符， 如“\${”.
* EL表达式可以返回任意类型的值。如果EL表达式的结果是一个带有属性的对
象，则可以利用“[”、“]”或者“.”运算符来访问该属性: ${object["propertyName"]} or ${object.propertyName}
* EL表达式的取值是从左到右进行的。

### 关键字

以下是关键字，它们不能用作标识符：

and, eq, gt, true, instanceof, or, ne, le, false, 
empty, not, lt, ge, null, div, mod.

### EL隐式对象

隐式对象            描述

pageContext     这是当前JSP的javax.servlet.jsp.PageContext

initParam       这是一个包含所有环境初始化参数，并用参数名作为key的Map

param           这是一个包含所有请求参数，并用参数名作为key的Map。
                每个key的值就是指定名称的第一个参数值。因此，如果两
                个请求参数同名，则只有第一个能够利用param获取值。要
                想访问同名参数的所有参数值，就得用params代替

paramValues     这是一个包含所有请求参数，并用参数名作为key的Map。每个key的值就是一个字符串数组，其中包含了指定参数名称的所有参数值。就算该参数只有一个值，它也仍然会返回一个带有一个元素的数组

header          这是一个包含请求标题，并用标题名作为key的Map。每个key的值就是指定标题名称的第一个标题。换句话说，如果一个标题的值不止一个，则只返回第一个值。要想获得多个值的标题，得用headerValues对象代替

headerValues    这是一个包含请求标题，并用标题名作为key的Map。每个key的值就是一个字符
                串数组，其中包含了指定标题名称的所有参数值。就算该标题只有一个值，它也仍然会返回一个带有一个元素的数组

cookie          这是一个包含了当前请求对象中所有Cookie对象的Map。 Cookie名称就是key名
                称，并且每个key都映射到一个Cookie对象
                
applicationScope 这是一个包含了ServletContext对象中所有属性的Map，并用属性名称作为key
sessionScope 这是一个包含了HttpSession对象中所有属性的Map，并用属性名称作为key
requestScope 这是一个Map，其中包含了当前HttpServletRequest对象中的所有属性，并用属
性名称作为key
pageScope 这是一个Map，其中包含了全页面范围内的所有属性。属性名称就是Map的key

EL表达式示例

access: http://localhost:8080/employee

此网页(employee.jsp)由EmployeeServlet转发而来，里面使用了EL表达式显示相关信息.

