# MySql索引

## 简介

索引是存储引擎用于快速查找记录的一种数据结构，通过合理的使用数据库索引可以大大提高系统的访问性能，接下来主要介绍在MySql数据库中索引类型，以及如何创建出更加合理且高效的索引技巧。

>注：这里主要针对的是InnoDB存储引擎的B+Tree索引数据结构.

MySQL索引的建立对于MySQL的高效运行是很重要的，索引可以大大提高MySQL的检索速度。

打个比方，如果合理的设计且使用索引的MySQL是一辆兰博基尼的话，那么没有设计和使用索引的MySQL就是一个人力三轮车。

## 索引分单列索引和组合索引

- 单列索引: 即一个索引只包含单个列，一个表可以有多个单列索引，但这不是组合索引
- 组合索引，即一个索引包含多个列

创建索引时，你需要确保该索引是应用在SQL 查询语句的条件(一般作为 WHERE 子句的条件)。

## 使用索引的弊端

上面都在说使用索引的好处，但过多的使用索引将会造成滥用。因此索引也会有它的缺点：虽然索引大大提高了查询速度，同时却会降低更新表的速度，如对表进行INSERT、UPDATE和DELETE。因为更新表时，MySQL不仅要保存数据，还要保存一下索引文件。

建立索引会占用磁盘空间的索引文件。

## 普通索引

这是最基本的索引，它没有任何限制。它有以下几种创建方式：

```sql
-- 如果是CHAR，VARCHAR类型，length可以小于字段实际长度；如果是BLOB和TEXT类型，必须指定 length。
CREATE INDEX indexName ON mytable(username(length)); 
```

>修改表结构(添加索引)

```sql
ALTER table tableName ADD INDEX indexName(columnName)
```

>创建表的时候直接指定

```sql
CREATE TABLE mytable(  
 
ID INT NOT NULL,   
 
username VARCHAR(16) NOT NULL,  
 
INDEX [indexName] (username(length))  
 
);  
```

>删除索引的语法

```sql
DROP INDEX [indexName] ON mytable; 
```

## 唯一索引

它与前面的普通索引类似，不同的就是：索引列的值必须唯一，但允许有空值。如果是组合索引，则列值的组合必须唯一。它有以下几种创建方式：

创建索引
CREATE UNIQUE INDEX indexName ON mytable(username(length))

修改表结构
ALTER table mytable ADD UNIQUE [indexName] (username(length))
创建表的时候直接指定
CREATE TABLE mytable(  
 
ID INT NOT NULL,   
 
username VARCHAR(16) NOT NULL,  
 
UNIQUE [indexName] (username(length))  
 
);