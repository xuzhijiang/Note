# 常见函数

- 单行函数: concat,length,ifnull等,这些函数的特点是传入一个或多个参数,最终返回一个值
- 分组函数(聚合函数/统计函数): 传入一组值,最终返回一个值,用于统计.

# 细分

- 字符函数: 用于对字符型的处理,传入的参数类型就是字符型.
- 数学函数: 用于对数值的处理,传入的参数类型就是数值型.
- 日期函数: 参数类型为日期型
- 其他函数
- 流程控制函数

![](../pics/单行函数练习01.png)
![](../pics/单行函数练习02.png)
![](../pics/单行函数练习03.png)

# 其他函数

```sql
-- 显示当前时间、用户名、数据库版本
SELECT now(), user(), version();

SELECT PASSWORD('llll');
SELECT MD5('aaa');

SELECT UNIX_TIMESTAMP();

SELECT UNIX_TIMESTAMP('2019-10-31 23:59:59');
```

# 基础练习

![](../pics/基础练习题.png)