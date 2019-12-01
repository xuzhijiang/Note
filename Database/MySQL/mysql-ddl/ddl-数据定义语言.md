# DDL-数据定义语言

![](../pics/ddl-对库和表结构的管理.png)

# 库的管理

![](../pics/库的管理01.png)
![](../pics/库的管理02.png)

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS test CHARACTER SET charset_name, COLLATE collation_name;
-- 删除数据库:
-- 注意：删除一个数据库将导致该数据库的所有表全部被删除。
drop database if exists <db_name>;
-- 修改数据库的字符集以及校对规则
ALTER DATABASE <DBNAME> CHARACTER SET utf8 COLLATE utf8_general_ci;

-- 查看创建数据库的SQL语句:
show create database db;
-- 查看当前正在使用的数据库
SELECT DATABASE();
-- 查看数据库
SHOW DATABASES;
-- 选择数据库
USE test;

-- 列出当前数据库的所有表：
SHOW TABLES;
-- 查看其他库的所有表
SHOW TABLES FROM  库名
```

# 表的管理

![](../pics/表的管理01.png)
![](../pics/表的管理02.png)
![](../pics/表的管理03.png)
![](../pics/表的管理04.png)
![](../pics/表的管理05.png)
![](../pics/表的管理06.png)
![](../pics/表的管理07.png)
![](../pics/表的管理08.png)
![](../pics/表的管理09.png)
![](../pics/表的管理10.png)
![](../pics/如何设置有符号和无符号.png)

```sql
CREATE TABLE IF NOT EXISTS `student` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` varchar(20) NOT NULL,
    `gender` varchar(2) NOT NULL,
    `language` varchar(10) DEFAULT 'English',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 要查看一个表的结构：
DESC students;

# 查看创建表的SQL语句:
SHOW CREATE TABLE students;

# 删除表
DROP TABLE IF EXISTS student;

-- 修改表
ALTER TABLE 表名 ADD|MODIFY|DROP|CHANGE COLUMN 列名 [类型 约束];

-- 给表添加列
ALTER TABLE students ADD COLUMN birth VARCHAR(10) NOT NULL;

-- 修改列名
ALTER TABLE students CHANGE COLUMN birth birthday VARCHAR(20) NOT NULL;

-- 修改表的某列的约束,注意新列的类型以及约束要和老的列的类型兼容
ALTER TABLE students MODIFY COLUMN name varchar(100);

-- 修改表使用的数据库引擎
ALTER TABLE 表名 ENGINE=MYISAM;

-- 修改表名
ALTER TABLE students RENAME [TO] student;
-- 或者
rename table old_table_name to new_table_name;

-- 可将表移动到另一个数据库
rename table old_table_name to new_database.new_table_name;

--删除列
ALTER TABLE students DROP COLUMN birthday;

-- 删除主键(删除主键之前一定要删除当前主键的外键约束)
alter table stu drop primary key;

-- 清空表中的所有数据: 
-- truncate不能加where条件，而delete可以加where条件,
-- truncate删除不能回滚，delete删除可以回滚
delete from <tablename>
truncate table 表名
```

# 标识列

![](../pics/标识列01.png)

```sql
SHOW VARIABLES LIKE '%auto_increment%';
SET auto_increment_increment = 1;
```

![](../pics/标识列02.png)

![](../pics/修改表时设置标识列.png)

# 如何在线上更新表结构

![](../pics/如何在线上更新表结构01.png)

![](../pics/如何在线上更新表结构02.png)