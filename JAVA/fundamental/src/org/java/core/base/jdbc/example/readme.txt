Java DataBase Connectivity（JDBC）API 在Java应用和关系型数据库服务器直接 
提供行业标准和独立于数据库的连接，以便与关系数据库一起使用。 

JDBC提供了从"Java程序"连接到"关系型数据库"的框架.

JDBC API helps us in writing loosely-coupled code for database connectivity.JDBC帮我们写松耦合的代码.

JDBC用来实现下述任务:

1. 建立到像Oracle，MySQL这样的关系型数据库服务器的连接，JDBC API没有提供
框架到NoSQL数据库(像MongoDB)的连接
2. 发送SQL查询到将要在数据库服务器上被执行的连接
3. 处理由执行查询返回的结果

JDBC API consists of two parts:

1. first part is the JDBC API to be used by the application programmers. 

2. Second part is the low-level API to connect to database server.

3. First part of JDBC API is part of standard java packages in java.sql package.
第一部分的JDBC API是标准的java包java.sql中的一部分.

For second part there are four different types of JDBC drivers:
对于第二部分，这里有4中类型的JDBC驱动:

						  Java Application
						        ^
						        |
							JDBC API
						 		^
						 		|
						  JDBC Driver Manager
			^                   ^							^							^
			|					|							|							|
JDBC ODBC Bridge Driver		Partial Java JDBC Driver	Pure Java JDBC Driver	 Pure Java JDBC Driver
			^
			|
		ODBC API
			^					^							^
			|					|							|		
	    DB Client Lib		DB Client Lib				DB Middleware Server
	    	^					^							^							^
	    	|					|							|							|
	    							Database Server
	    							
	    								
1. JDBC-ODBC Bridge plus ODBC Driver (Type 1): 过时了.
2. Native API partly Java technology-enabled driver (Type 2): this is also not preferred driver.(这也不是首选的驱动程序。)	
3. Pure Java Driver for Database Middleware (Type 3): This adds to extra 
network calls and slow performance. Hence this is also not widely used JDBC driver.
这会增加额外的网络呼叫并降低性能。因此这也没有广泛使用的JDBC驱动程序				   
4. Direct-to-Database Pure Java Driver (Type 4):这是首选驱动程序，
因为它将JDBC调用转换为数据库服务器可以理解的网络协议。此解决方案不需要客户端的任何额外API，并且适合通过网络进行数据库连接。
但是对于此解决方案，我们应该使用特定于数据库的驱动程序，例如Oracle for Oracle DB提供的OJDBC 
jar和MySQL Connector for J for MySQL数据库。   

使用JDBC API时，数据库配置是最重要的细节。 我们应该知道的第一件事是要使用的Driver类。
 对于Oracle数据库，驱动程序类是oracle.jdbc.driver.OracleDriver。 对于MySQL数据库，
 驱动程序类是com.mysql.jdbc.Driver。 您将在各自的驱动程序jar文件中找到这些驱动程序类。
  这两个都实现了JDBC java.sql.Driver接口。
  
MySQL数据库连接字符串格式为jdbc:mysql://<HOST>:<PORT>/<SCHEMA>.

Oracle数据库连接字符串格式为jdbc：jdbc:oracle:thin:@<HOST>:<PORT>:<SID>.

