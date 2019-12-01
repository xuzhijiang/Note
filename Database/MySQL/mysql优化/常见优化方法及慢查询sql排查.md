# exist和in用哪个

```sql
-- 如果主查询的数据集大,则使用in(效率高)
-- 如果子查询的数据集大,则使用exist(效率高)

-- exist语法
select ... from table(主查询) where exists (子查询)

--- in语法
select * from <table_name>(主查询) where 字段 in (子查询)
```

```sql
-- 等价于select tname from teacher
-- 将主查询的结果放到子查询的结果中进行条件校验,看主查询的数据是否在子查询
-- 如果在,则保留主查询的数据
select tname from teacher where exists (select * from teacher);
select tname from teacher where exists (select * from teacher where tid = 999);

-- in
select tid from teacher where tid in (select tid from teacher);
```

# order by 优化

order by用的比较多,所以要深入的说一下.

`using filesort`底层有两种算法：双路排序、单路排序 （根据IO的次数）

>MySQL4.1之前 默认使用 双路排序； 双路：扫描2次磁盘

- 第1次：从磁盘读取需要排序字段,对排序字段进行排序（在buffer缓存中进行的排序）   
- 第2次：扫描其他字段不用排序的字段,

>因为IO较消耗性能,为了减少io访问次数,MySQL4.1之后 默认使用 单路排序 ： 只读取一次（全部字段），在buffer中进行排序。但这种单路排序 会有一定的隐患 （不一定真的是“单路/1次IO”，有可能多次IO）。原因：如果数据量特别大，则无法 将所有字段的数据 一次性读取完毕，因此 会进行“分片读取、多次读取”。

- 注意：单路排序 比双路排序 会占用更多的buffer。
- 单路排序在使用时，如果数据大，可以考虑调大buffer的容量大小：`set max_length_for_sort_data = 1024  单位byte`,如果`max_length_for_sort_data`值太低，则mysql会自动从 单路->双路  （到底什么叫太低：需要排序的列的总大小超过了max_length_for_sort_data定义的字节数）

```sql
show variables like '%sort%';
set max_length_for_sort_data = 1024;
```

---
    提高order by查询的策略：
    
    a.选择使用单路、双路 ；调整buffer的容量大小；
    b.避免select * ...  ,程序还要计算*都包含了哪些列,所以*肯定很慢,而且大部分时候我们不需要*,用什么列指定什么就可以了.
    c.复合索引 不要跨列使用 ，避免using filesort
    d.保证全部的排序字段 排序的一致性（都是升序 或 降序）
---
	

# 生成海量数据

  	a.模拟海量数据,通过存储过程或存储函数来模拟海量数据         [存储过程（语法上无return返回值）/存储函数（语法上有return返回值）]

```sql
create database testdata ;
use testdata

create table dept
(
dno int(5) primary key default 0,
dname varchar(20) not null default '',
loc varchar(30) default ''
)engine=innodb default charset=utf8;

create table emp
(
eid int(5) primary key,
ename varchar(20) not null default '',
job varchar(20) not null default '',
deptno int(5) not null default 0
)engine=innodb default charset=utf8;
-- 部门表和员工表通过部门编号连接起来

```

```sql
-- 通过存储函数 插入海量数据：
-- 创建存储函数:
-- randstring相当于方法的名字(或者叫函数的名字),这个方法的名字后面需要写一个数字,写个6,就产生6位随机字符串,用于模拟员工名称
randstring(6)  ->aXiayx  
```

```sql
-- 产生随机字符串的函数
-- 告诉程序，封号;不是结束符，$才是结束符，也就是指定结束符，这样程序遇到；的时候，不会结束而是只会当成一条语句。防止；造成语义中断。
delimiter $

-- randstring是函数的名字,输入参数为int类型,因为存储函数有返回值,我们加上returns,我们要返回字符串,所以加上varchar(255),这个返回的长度可以自己定义.
-- 这里相当于定义标题
-- 注意返回时returns
create function randstring(n int)   returns varchar(255)
--  
begin
    -- 定义一个变量all_str,是一个字符串类型,所以写上varchar(100),默认值是后面的一串,也就是随机字符串的值是从哪里来的,从后面这一串中挑选.
    -- varchar(100) 100个值完全可以包含后面那一串了
    declare  all_str varchar(100) default 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ' ;
    -- 我们挑完并且拼接之后的值通过return_str返回.
    declare return_str varchar(255) default '' ;
    -- 定义一个i，默认是0，也就是我们打算从0->我们传进来的n进行循环。
    declare i int default 0 ; 
    -- 这里是一个循环
    while i<n		 
    do									
        -- set就是给return_str设置值得意思，concat是拼接得意思，相当于是+=,给return_str拼接字符串
        -- substring是截取函数，mysql中对于'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'，a是第1位，不是第0位
        -- substring(all_str, 5, 1): 意思是从all_str的第5位开始截取，截取1位，也就是每次拿一个值
        -- rand(): 产生一个从0到1的随机数，左闭右开，所以rand() * 52 的范围是[0,52), rand()*52+1的范围就是[1,53)
        -- floor就是取下整，所以FLOOR(1+rand()*52)范围就是[1,52]
        -- 每次给return_str拼接一个值，
        set return_str = concat(return_str, substring(all_str,  FLOOR(1+rand()*52),   1));
        -- 循环n次，所以return_str长度为6个字符
        set i=i+1 ;
    -- 循环结束
    end while ;
    -- 第一个return是语法，第二个是值
    return return_str;
end $ 

--如果报错：You have an error in your SQL syntax，说明SQL语句语法有错，需要修改SQL语句；
-- 如果报错This function has none of DETERMINISTIC, NO SQL, or READS SQL DATA in its declaration and binary 
-- logging is enabled (you *might* want to use the less safe log_bin_trust_function_creators variable)
-- 是因为 存储过程/存储函数在创建时 与之前的 开启慢查询日志冲突了 (因为开了慢查询日志导致)
```

```sql
-- 解决冲突：
-- 临时解决( 开启log_bin_trust_function_creators )
show variables like '%log_bin_trust_function_creators%';
set global log_bin_trust_function_creators = 1;
-- 永久解决：
-- /etc/my.cnf 
-- [mysqld]
-- log_bin_trust_function_creators = 1
```

```sql
--产生随机整数的函数
create function ran_num() returns int(5)
begin
    declare i int default 0;
    -- floor(rand()*100): [0,99]
    set i =floor( rand()*100 ) ;
    return i ;
-- 因为上面已经设置$为分隔符了，所以这里还是使用$
end $
```   
  	
```sql
-- 上面是通过存储函数实现的，这里使用存储过程
-- 通过存储过程插入海量数据,来创建emp表中的数据：
-- emp表中  ，  10000,   100000

-- 创建一个存储过程(procedure)
-- 存储函数有return，存储过程没有return，使用in和out表示输入和输出
-- in表示是输入参数： eid_start,是int类型，data_times也是输入类型参数，
-- eid_start表示员工id从多少开始的一个变量参数
-- data_times: 从eid_start开始，插入data_times个数据
-- 比如eid_start=1万，data_times为1万，那意思就是从1万开始，插一万条数据
create procedure insert_emp(in eid_start int(10),in data_times int(10))
begin 
    declare i int default 0;
    -- 关闭自动提交，插入的时候不提交，最后一次性提交
    set autocommit = 0 ;
    -- 通过循环来插入
    repeat
        -- 从eid_start开始插入， randstring(5)就是使用上面我们定义的存储函数
        -- job随便写为other，ran_num()上面的随机整数存储函数
        insert into emp values(eid_start + i, randstring(5) ,'other' ,ran_num()) ;
        set i=i+1 ;
        -- 循环结束条件，until这句不需要分号结尾
        until i=data_times
    end repeat ;
    commit ;
end $
```

```sql
--通过存储过程插入海量数据：dept表中  
create procedure insert_dept(in dno_start int(10) ,in data_times int(10))
begin
    declare i int default 0;
    set autocommit = 0 ;
    repeat
        insert into dept values(dno_start+i ,randstring(6),randstring(8)) ;
        set i=i+1 ;
        until i=data_times
    end repeat ;
commit ;
end$
```

```sql
--插入数据,调用上面的存储过程
-- 把分隔符从$改为分号
delimiter ; 
call insert_emp(1000,800000) ;
call insert_dept(10,30) ;
-- 验证
select count(1) from emp;
select count(1) from dept;
```

# 全局查询日志

```sql
-- (3)全局查询日志
-- 会记录开关打开 之后的 全部SQL语句，再开关没有打开之前的sql不会被记录
-- （这个全局的sql记录操作开关 仅仅在调优、开发过程中打开即可，在最终的部署实施时 一定关闭）
show variables like '%general_log%';

--执行的所有SQL都会记录在 mysql.general_log这个系统数据库表中
set global general_log = 1 ; --开启全局日志
--将全部的SQL 记录在表中,就是上面的general_log这个表
set global log_output='table' ;
-- 测试下
select * from emp;
-- 查看记录的sql
use mysql
select * from general_log;


-- 还可以将 所有的SQL 记录在文件中
set global log_output='file' ; -- 设置输出到文件
set global general_log = on ;
set global general_log_file='/tmp/general.log' ;

-- 开启后，会记录所有SQL ： 会被记录 mysql.general_log表中。
select * from  mysql.general_log ;		
```
