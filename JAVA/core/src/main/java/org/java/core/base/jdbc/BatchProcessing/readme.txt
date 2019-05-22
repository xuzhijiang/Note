JDBC Batch insert and update examples in MySQL and Oracle databases. 

 有时我们需要为数据库运行类似的批量查询，例如将数据从CSV文件加载到关系数据库表。 
 我们知道我们可以选择使用Statement或PreparedStatement来执行查询。
  除此之外，JDBC还提供了批处理功能，通过该功能，我们可以一次性为数据库执行大量查询。
  
JDBC batch statements are processed through Statement and PreparedStatement 
addBatch() and executeBatch() methods.


MYSQL DB	STATEMENT	PREPAREDSTATEMENT	STATEMENT BATCH	PREPAREDSTATEMENT BATCH
Time Taken (ms)	8256	8130	7129	7019

当我查看响应时间时，我不确定它是否正确，因为我希望通过批处理能够改善响应时间。 所以我在网上看了一些解释，
 and found out that by default MySQL batch processing works in 
 similar way like running without batch. 
 发现默认MySQL批处理的工作方式与没有批处理的情况类似。 为了获得MySQL中批处理的实际好处，
 我们需要在创建数据库连接时将rewriteBatchedStatements传递为TRUE:
 
#DB_URL=jdbc:mysql://localhost:3306/UserDB?rewriteBatchedStatements=true

将rewriteBatchedStatements设置为true，下表提供了相同程序的响应时间。


MYSQL DB	STATEMENT	PREPAREDSTATEMENT	STATEMENT BATCH	PREPAREDSTATEMENT BATCH
Time Taken (ms)	5676	5570	3716	394

As you can see that PreparedStatement Batch Processing is very fast 
when rewriteBatchedStatements is true. So if you have a lot of 
 processing involved, you should use this feature for faster processing.
 正如您所看到的，当rewriteBatchedStatements为true时，PreparedStatement Batch Processing非常快。
  因此，如果涉及大量批处理，则应使用此功能以加快处理速度。

当我执行Oracle数据库的上述程序时，结果与MySQL处理结果一致，PreparedStatement Batch处理比任何其他方法快得多。



