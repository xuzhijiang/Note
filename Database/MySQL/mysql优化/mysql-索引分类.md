# 索引分类

- 单值索引: 单列,比如给age列加索引,这个age列就是单值索引,一个表可以有多个单值索引,比如我再给这个表的name列增加索引,也是可以的.
- 唯一索引: 唯一索引不能重复,比如age,就不可以做唯一索引,比如很多人都是23岁,一般用id列建立唯一索引
- 复合索引: 由多个列构成的,相当于书的二级目录,比如我要找"赵",先找z这个目录,然后再在z这个目录里面找zhao,相当于找了2次.(name和age共同组成一个复合索引,也就是先根据name找人,如果name重复了,再根据age筛选一次,相当于一共筛选了2次),复合索引不一定所有列都要用,例如如果name列已经找到了,age列就不用了.
- 主键索引: (注意,一个table,一旦有了主键,就相当于自动有了主键索引),所以增加主键索引的过程就是设置主键的过程,索引的名字就是"PRIMARY"

# 第一种方式创建索引

    create 索引类型 索引名 on 表 (字段,字段)

```sql
create database myBD;

create table tb (
id int(4) not null auto_increment,
name varchar(5),
dept varchar(5),
primary key (id)
) engine=InnoDB auto_increment=1 
default charset=utf8;
```

>单值索引

```sql
-- 单值索引的列中的值可以重复
create index dept_index on tb(dept);
```

>唯一索引

```sql
-- 唯一索引列中的值不可以重复.
create unique index index_name on table_name (column1,column2,...);
```

>复合索引

```sql
create index dept_name_index on tb(dept,name);
```

# 第二种方式创建索引

    alter table <table_name> add 索引类型 索引名(字段)

```sql
drop table tb;

create table tb (
id int(4) not null auto_increment,
name varchar(5),
dept varchar(5),
primary key (id)
) engine=InnoDB auto_increment=1 
default charset=utf8;
```

>单值索引

```sql
alter table tb add index dept_index(dept);
```

>唯一索引

```sql
alter table tb add unique index name_index(name);
```

>全文索引

```sql
-- 创建专用于文本搜索的 FULLTEXT 索引
-- 鸡肋产品,弃之不舍,留之无用
ALTER TABLE tbl_name ADD FULLTEXT index_name (column_list)
```

>复合索引

```sql
alter table tb add index dept_name_index(dept,name);
```    

事务操作只对DML有效,也就是只对增删改有效,而alter是ddl,ddl会自动提交,不管是create,drop还是alter,全是ddl语句,程序遇到ddl会自动commit,不需要手动commit.

注意: 如果一个字段是primary key,则该字段默认就是主键索引,会自动对主键建立索引,主键索引和唯一索引很像,都是不能重复,区别是主键不能为null,唯一索引可以是null.也就是如果加了主键索引,该列的值不能为null,如果列是唯一索引,列的值可以为null.

# 删除索引

    drop index 索引名 on table

```sql
drop index name_index on tb;
```     

# 查询索引

    show index from <table_name>;

```sql
show index from tb;

show create table table_name;
```