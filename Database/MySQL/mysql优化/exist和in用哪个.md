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
