# SQL

SQL是结构化查询语言Structured Query Language的缩写.标准 SQL 由 ANSI 标准委员会管理，从而称为 ANSI SQL。各个 DBMS 都有自己的实现

    RDBMS: Relational database management system
    
## 主流关系数据库

>目前，主流的关系数据库主要分为以下几类：

1. 商用数据库，例如：Oracle，SQL Server，DB2等；
2. 开源数据库，例如：MySQL，PostgreSQL等；
3. 桌面数据库，以微软Access为代表，适合桌面应用程序使用；
4. 嵌入式数据库，以Sqlite为代表，适合手机应用和桌面程序。

## SQL语言几种操作数据库的能力：

1. DDL：Data Definition Language(创建表、删除表、修改表结构-由数据库管理员执行)
2. DML：Data Manipulation Language(添加、删除、更新数据的能力-应用程序对数据库的日常操作)
3. DQL：Data Query Language(查询数据-最频繁的数据库日常操作)

## 语法特点

SQL语言`关键字`不区分大小写！！！但是，针对不同的数据库，对于`表名`和`列名`，有的数据库区分大小写，有的数据库不区分大小写。同一个数据库，有的在Linux上区分大小写，有的在Windows上不区分大小写, 所以，本教程约定：`SQL关键字`总是大写，以示突出，`表名`和`列名`均使用`小写`。

**注释**

- 单行注释：#注释文字
- 单行注释：-- 注释文字
- 多行注释：/* 注释文字  */

## 关系模型

表的每一行称为记录(Record）.表的每一列称为字段(Column）

>字段定义了数据类型(整型、浮点型、字符串、日期等），以及是否允许为NULL。注意NULL表示字段数据不存在。一个整型字段如果为NULL不表示它的值为0，同样的，一个字符串型字段为NULL也不表示它的值为空串''

> 通常情况下，字段应该避免允许为NULL。不允许为NULL可以简化查询条件，加快查询速度，也利于应用程序读取数据后无需判断是否为NULL。
