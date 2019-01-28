1. 当用户提交表单时，在表单元素中输入的值就会被当作请求参数发送到服务器
2. Input元素的值，会被当作字符串发送到服务器
3. 包含多个值的select元素（允许选择多个选项并且用<select multiple>表示的select元素）发出一个字符串数组，并且必须通过SelectRequest.getParameterValues进行处理
4. 复选框比较奇特。选中的复选框会发送字符串“on”到服务器。未选中的复选
框则不向服务器发送任何内容， ServletRequest.getParameter(fieldName)
返回null
5. 单选框将被选中按钮的值发送到服务器。如果没有选择任何按钮，将没有任
何内容被发送到服务器， 并且ServletRequest.getParameter(fieldName)
返回null。
6. 如果一个表单中包含多个输入同名的元素，那么所有值都会被提交，并且必
须利用ServletRequest.getParameterValues来获取它们。ServletRequest.getParameter将只返回最后一个值。

> 在真实的Java Web项目中，是不会使用Servlet来生成表单的，最差也是使用JSP来设计，如果是Spring Boot，则可以使用Thymeleaf等来设计，而在“前后端分离”的Web应用中，表单是由前端框架
（比如Angular/Vue/React）生成。所以，对于Servlet生成表单，只需要了解即可
