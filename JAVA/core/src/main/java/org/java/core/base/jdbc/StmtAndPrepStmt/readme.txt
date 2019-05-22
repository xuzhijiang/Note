因为PreparedStatement是预编译的,is pre-compiled, we can’t use it with
IN clause. Rather than going back to Statement, there are some 
alternative approaches that we can use to get over this shortcomings 
of Prepared Statement. This article provides four different 
alternative approaches that we can take to support IN clause
 with prepared statements. You should read it because you never 
 know when you are going to need it, also it’s one of the most 
 asked interview question related to JDBC.

使用PreparedStatement的另一个好处是我们可以通过addBatch（）和executeBatch（）
方法使用批处理。我们可以创建一个预准备语句并使用它来执行多个查询。

Some points to remember about JDBC PreparedStatement are:

PreparedStatement帮助我们防止SQL注入攻击，因为它会自动转义特殊字符。
PreparedStatement允许我们使用参数输入执行动态查询。
PreparedStatement提供了不同类型的setter方法来设置查询的输入参数。
PreparedStatement比Statement快。当我们重用PreparedStatement或使用它的批处理方法执行多个查询时，它变得更加明显。
PreparedStatement帮助我们使用setter方法编写面向对象的代码，而使用Statement我们必须使用String Concatenation来创建查询。如果要设置多个参数，则使用字符串连接编写查询看起来非常难看并且容易出错。
PreparedStatement返回FORWARD_ONLY ResultSet，因此我们只能向前移动。
与Java Arrays或List不同，PreparedStatement变量的索引从1开始。
PreparedStatement的一个限制是我们不能将它用于带有IN子句的SQL查询，因为PreparedStatement不允许我们为单个占位符（？）绑定多个值。但是，使用PreparedStatement for IN子句的替代方法很少，请在JDBC PreparedStatement IN子句中阅读更多内容。
这就是JDBC Statement与PreparedStatement的比较。您应该始终使用PreparedStatement，因为它快速，面向对象，动态且更可靠。


