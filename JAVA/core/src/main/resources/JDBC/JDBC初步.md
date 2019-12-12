# JDBC(Java DataBase Connectivity)

    JDBC：是JDK定义的一套操作所有关系型数据库的接口。各个数据库厂商需要去实现这套接口，提供数据库驱动jar包。
    我们可以使用这套接口（JDBC）编程，真正执行的代码是驱动jar包中的实现类。

![](../pics/JDBC接口的位置.png)

![](../pics/1566569819744.png)

# java.sql.Driver标准接口类

    // 不同的数据库厂商，需要针对这套接口，提供不同实现——面向接口编程
    public interface Driver {
        // 最重要的方法,各个数据库厂商需要去实现这个接口
        Connection connect(String url, java.util.Properties info) throws SQLException;
    }

# DriverManager(驱动管理)

    功能： 
    
    1. 通过 Class.forName("com.mysql.jdbc.Driver"); 将类com.mysql.jdbc.Driver加载到内存中.
    通过查看源码发现：在com.mysql.jdbc.Driver类中存在静态代码块
    static静态代码块在类加载到内存的时候就会执行,也就是将com.mysql.jdbc.Driver注册到DriverManager
    
    static {
        try {
            java.sql.DriverManager.registerDriver(new Driver()); // 注册与给定的驱动
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    2. 获取数据库连接：
    DriverManager.getConnection()方法使用已经注册的驱动com.mysql.jdbc.Driver去创建
    数据库连接，如果在获取数据库的过程中遇到了任何问题，此方法会抛出java.sql.SQLException
    
    DriverManager.getConnection()方法内部就是通过java.sql.Driver的Connection connect(String url, java.util.Properties info)
    来获取数据库连接的
    static Connection getConnection(String url, String user, String password)

# Connection(数据库连接对象)

    功能：

    1. 获取执行sql的对象
        Statement createStatement()
        PreparedStatement prepareStatement(String sql)  
        
    2. 管理事务：
        开启事务：setAutoCommit(boolean autoCommit) ：调用该方法设置参数为false，即开启事务
        提交事务：commit() 
        回滚事务：rollback() 

# Statement(执行sql的对象)

    功能: 用于执行静态 SQL 语句
        1. boolean execute(String sql) ：可以执行任意的sql 了解 
        2. int executeUpdate(String sql) ：执行DML（insert、update、delete）语句、DDL(create，alter、drop)语句
            * 返回值：影响的行数，可以通过这个影响的行数判断DML语句是否执行成功 返回值>0的则执行成功，反之，则失败。
        3. ResultSet executeQuery(String sql)  ：执行DQL（select)语句
    
    缺点: 会有sql注入的问题.而且需要手动拼接,比较繁琐,容易出错.

# PreparedStatement(执行sql的对象)
    
    功能: SQL 语句被预编译并存储在此对象中
    1. SQL注入问题：在拼接sql时，有一些sql的特殊关键字参与字符串的拼接。会造成安全性问题

        输入用户随便，输入密码：’a’ or ‘a’ = ‘a’
        sql：select username,password from user where username = ‘aaa’ and password = ‘a’ or ‘a’ = ‘a’

    2. 解决sql注入问题：对于 Java 而言，要防范 SQL 注入，只要用 PreparedStatement取代 Statement 就可以了
    3. 预编译的SQL：参数使用?作为占位符
    4. 定义sql
        注意：sql的参数使用？作为占位符。 如：select * from user where username = ? and password = ?;
        获取执行sql语句的对象 PreparedStatement = Connection.prepareStatement(String sql)
    5. 给？赋值：
        方法： setXxx(参数1,参数2)
            * 参数1：？的位置编号,从1 开始
            * 参数2：？的值
    7. 执行sql，接受返回结果，不需要传递sql语句

注意：后期都会使用PreparedStatement来完成增删改查的所有操作,可以防止SQL注入,而且效率更高

# CallableStatement

    用于执行 SQL 存储过程

# ResultSet(结果集对象,封装查询结果)

    方法:
        boolean next(): 游标向下移动一行，判断是否还有下一行数据，如果有返回false，如果没有返回true
        getXxx(参数):获取数据
            * Xxx：代表数据类型   如： int getInt() ,	String getString()
            * 参数：
                1. int：代表列的编号,从1开始   如： getString(1)
                2. String：代表列名称。 如： getDouble("balance")

    使用步骤：
    while(rs.next()){ //循环判断游标是否是最后一行末尾。
        // 获取数据
        int id = rs.getInt(1);
        String name = rs.getString("name");
        double balance = rs.getDouble(3);
        System.out.println(id + "---" + name + "---" + balance);
    }

# ResultSetMetaData
  
- 可用于获取关于 ResultSet 对象中列的类型和属性信息的对象

- ResultSetMetaData meta = rs.getMetaData();
- **getColumnName**(int column)：获取指定列的名称
- **getColumnLabel**(int column)：获取指定列的别名
- **getColumnCount**()：返回当前 ResultSet 对象中的列数。 

- getColumnTypeName(int column)：检索指定列的数据库特定的类型名称。 
- getColumnDisplaySize(int column)：指示指定列的最大标准宽度，以字符为单位。 
- **isNullable**(int column)：指示指定列中的值是否可以为 null。 

-  isAutoIncrement(int column)：指示是否自动为指定列进行编号，这样这些列仍然是只读的。 

# 资源的释放

- 释放ResultSet, Statement,Connection。
- 数据库连接（Connection）是非常稀有的资源，用完后必须马上释放，如果Connection不能及时正确的关闭将导致系统宕机。Connection的使用原则是**尽量晚创建，尽量早的释放。**
- 可以在finally中关闭，保证及时其他代码出现异常，资源也一定能被关闭

# JDBC控制事务

    事务：一个包含多个步骤的业务操作。这么多步骤要么同时成功，要么同时失败。

    操作：
        开启事务
        提交事务
        回滚事务

    使用Connection对象来管理事务
        1. 在执行sql之前开启事务：setAutoCommit(boolean autoCommit) ：调用该方法设置参数为false，即开启事务
        2. 当所有sql都执行完提交事务,提交事务：commit()
        3. 在catch中回滚事务：rollback()
    
    如果我们在使用数据库连接池,Connection 还可能被重复使用,如果我们使用过程中setAutoCommit(false)了,
    那么在归还Connection之前(也就是执行close方法之前),需要恢复其自动提交状态 setAutoCommit(true),

# JDBC URL

- JDBC URL的标准由三部分组成，各部分间用冒号分隔。 
  - **jdbc:子协议:子名称**
  - **协议**：JDBC URL中的协议总是jdbc 
  - **子协议**：子协议用于标识一个数据库驱动程序
  - **子名称**：一种标识数据库的方法。子名称可以依不同的子协议而变化，用子名称的目的是为了**定位数据库**提供足够的信息。包含**主机名**(对应服务端的ip地址)**，端口号，数据库名**

- **几种常用数据库的 JDBC URL**

  - MySQL的连接URL编写方式：

    - jdbc:mysql://主机名称:mysql服务端口号/数据库名称?参数=值&参数=值
    - jdbc:mysql://localhost:3306/mydb
    - jdbc:mysql://localhost:3306/mydb**?useUnicode=true&characterEncoding=utf8**（如果JDBC程序与服务器端的字符集不一致，会导致乱码，那么可以通过参数指定服务器端的字符集）
    - jdbc:mysql://localhost:3306/mydb?user=root&password=123456

  - Oracle 9i的连接URL编写方式：

    - jdbc:oracle:thin:@主机名称:oracle服务端口号:数据库名称
    - jdbc:oracle:thin:@localhost:1521:mydb

  - SQLServer的连接URL编写方式：

    - jdbc:sqlserver://主机名称:sqlserver服务端口号:DatabaseName=数据库名称

    - jdbc:sqlserver://localhost:1433:DatabaseName=mydb  

# Java与SQL对应数据类型转换表

| Java类型           | SQL类型                  |
| ------------------ | ------------------------ |
| boolean            | BIT                      |
| byte               | TINYINT                  |
| short              | SMALLINT                 |
| int                | INTEGER                  |
| long               | BIGINT                   |
| String             | CHAR,VARCHAR,LONGVARCHAR |
| byte   array       | BINARY  ,    VAR BINARY  |
| java.sql.Date      | DATE                     |
| java.sql.Time      | TIME                     |
| java.sql.Timestamp | TIMESTAMP                |    