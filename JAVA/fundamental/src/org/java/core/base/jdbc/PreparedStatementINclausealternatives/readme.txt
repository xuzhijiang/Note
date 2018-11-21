JDBC API provides two ways to communicate with database – 
Statement and PreparedStatement. Statement is easy to use 
but it can lead to SQL injection, that is very common way of 
hacking any application. This article clearly shows how SQL 
injection can be performed with SQL Statements and why we 
should use PreparedStatement to avoid SQL injection attacks.

This article goes further in explaining some of the major 
benefits we get from using PreparedStatement over Statement 
such as caching, object oriented programming and elegant looking code.

由于JDBC API只允许一个“？”参数使用一个文字,因此PreparedStatement不适用于IN子句(clause)查询(queries)。

因此，如果我们需要使用IN子句执行数据库查询，我们需要寻找一些替代方法。 本文的目的是分析不同的方法，您可以选择适合您要求的方法。

Execute Single Queries
Using Stored Procedure
Creating PreparedStatement Query dynamically
Using NULL in PreparedStatement Query


Using Stored Procedure

We can write a stored procedure and send the input data to the stored procedure.
 Then we can execute queries one by one in the stored procedure and 
 get the results. This approach gives fastest performance but as we 
 all know that Stored Procedures are database specific. So if our 
 application deals with multiple types of databases such as Oracle,
  MySQL then it will become hard to maintain. We should use this 
  approach only when we are working on single type of database and 
  there is no plan to change the database server. Since writing 
  stored procedure is out of scope of this tutorial, I will not 
  demonstrate how to use it.
  
使用存储过程
我们可以编写存储过程并将输入数据发送到存储过程。
 然后我们可以在存储过程中逐个执行查询并获得结果。 这种方法提供了最快的性能，
 但我们都知道存储过程是特定于数据库的。 因此，如果我们的应用程序处理多种类型的数据库
 （如Oracle，MySQL），那么它将变得难以维护。 我们应该只在我们处理单一类型的数据库时才使用这种方法，
 并且没有计划更改数据库服务器。 由于编写存储过程超出了本教程的范围，因此我不会演示如何使用它。
 
 
 Notice that the query is created dynamically and it will run perfectly. 
 There will be only one database call and the performance will be good.
  However if the size of user input varies a lot, we won’t get the 
  PreparedStatement benefit of caching and reusing the execution plan. 
  If you are not worried about PreparedStatement caching and there are
   not many queries with IN clause, then it seems to be the way to go.
   
 
请注意，查询是动态被创建的，它将完美运行。 只有一个数据库调用，性能会很好。 
但是，如果用户输入的大小变化很大，我们将无法获得PreparedStatement的缓存和重用执行计划的好处。
 如果你不担心PreparedStatement缓存，并且没有很多带有IN子句的查询，那么它似乎是要走的路(这种情况下此方式是可行的)。
 
 If you really want to utilize the PreparedStatement caching feature, then another 
 approach is to use NULL in PreparedStatement parameters.
 
 如果您确实想要使用PreparedStatement缓存功能，那么另一种方法是在PreparedStatement参数中使用NULL。


这就是我们在查询中使用PreparedStatement for IN子句的不同选项。 您可以根据项目要求使用其中任何一个。