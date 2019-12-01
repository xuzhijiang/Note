# profile

![](../pics/profile信息.png)

```sql
--（1）profiles
-- 默认关闭
show profiles ;
show variables like '%profiling%';
set profiling = on ; 

-- 通过show profiles ;分析执行时间
show profiles ;
-- show profiles;的作用：会记录所有profiling打开之后的  全部SQL查询语句所花费的时间。
-- 缺点：不够精确，只能看到 总共消费的时间，不能看到各个硬件消费的时间（cpu  io ）

-- (2)--精确分析(cpu/io):sql诊断
show profile all for query Query_Id(上一步查询的的)

| Status               | Duration |
| starting             | 0.000607 | 初始化耗时
waiting for query cache lock: 等待为查询缓存加锁的时间
checking query cache for query: 监测是否有查询缓存.(要去判断下这个查询是否有查询缓存可用)
| checking permissions | 0.000014 | 检查是否有权限
| Opening tables       | 0.000130 | 0.000000 |   0.000000 |                 0 |                   0 |            0 |             0 |             0 |                 0 |                 0 |                 0 |     0 | open_tables           | sql_base.cc          |        5753 |
| init                 | 0.000182 | 初始化
| System lock          | 0.000088 | 加锁.
| optimizing           | 0.000010 | 优化
| statistics           | 0.000015 |统计信息(统计信息之后会形成sql执行计划)
| preparing            | 0.000017 | 预处理
creating tem table : 创建临时表  
sorting result: 对结果进行排序
| executing            | 0.000005 | 执行
| Sending data         | 0.000561 | 提取数据
creating sort index: 创建排序的索引,说白了就是排序.
| end                  | 0.000035 |结束任务
removing tmp table: 删除临时表
| query end            | 0.000010 | 查询结束
| closing tables       | 0.000011 | 
| freeing items        | 0.000053 | 
| cleaning up          | 0.000054 | 做一下清理

-- 只显示cpu相关的和阻塞io相关的
show profile cpu,block io for query 上一步查询的的Query_Id
```

