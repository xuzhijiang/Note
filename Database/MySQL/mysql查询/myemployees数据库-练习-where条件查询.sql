-- select 常量/表达式/函数

-- select 常量
-- 但是，不带FROM子句的SELECT语句有一个有用的用途，就是用来判断当前到数据库的连接是否有效。许多检测工具会执行一条SELECT 1;来测试数据库连接。
SELECT 1;

-- 虽然SELECT可以用作计算，但它并不是SQL的强项。
-- 计算,as是起别名的意思,可以提高可读性
SELECT 100%98 AS 结果;
-- 起别名有2种方式,一种是as,一种是空格
SELECT 100%98 结果;

-- distinct是去重的意思
SELECT DISTINCT department_id FROM employees;
-- DISTINCT后面只能加一个字段,下面这种写法有问题,因为会造成last_name没有全部给查询出来.
SELECT DISTINCT department_id,last_name FROM employees;

# mysql中的+号仅仅一个功能: 运算符
SELECT '123'+90; # '123'会转成数字
SELECT 'john'+90; # 'john'转成数字失败,所以就是0
SELECT NULL+10; # 只要一方为null,结果就是null

# 这个+做拼接有问题,first_name和last_name都会转换数字失败,都是0,我们不这么玩,而是使用concat
SELECT first_name+last_name AS 姓名 FROM employees;

SELECT CONCAT('a','b','c') AS str;
SELECT CONCAT(first_name, last_name) AS 姓名 FROM employees;

SELECT CONCAT(first_name,',',last_name,',',email) AS "out put" FROM employees;

# concat连接的时候,一旦拼接的参数中有null,结果就是null
SELECT CONCAT(first_name,',',last_name,',',commission_pct) AS "out put" FROM employees;

# ifnull(arg1, arg2): 如果arg1为null,那么默认值就是arg2
SELECT IFNULL(commission_pct,0) AS 奖金率,commission_pct FROM employees;

SELECT CONCAT(first_name,',',last_name,',',IFNULL(commission_pct,0)) AS "out put" FROM employees;

# is null仅仅可以判断是否为null值
# is not null仅仅可以判断是否不为null值

# 安全等于也可以判断一个字段是否为null: <=>
# 安全等于也可以判断是否等于普通值,安全等于用的少,因为可读性比较差
SELECT * FROM employees WHERE commission_pct <=> NULL;

# 查询员工号为176的员工的姓名和部门编号和年薪
SELECT last_name,department_id,salary*12*(1+IFNULL(commission_pct, 0)) AS 年薪 FROM employees; 

# 查询departments表中涉及到哪些位置编号
SELECT DISTINCT location_id FROM departments;

# 经典面试题
# select * from employees; 和 select * from employees where commission_pct like '%%' and last_name like '%%';结果是否一样,请说明原因
# 不一样!!,如果判断的字段有null值,结果就不一样了,判断的字段有null值不会被查询出来.

# isnull是一个函数,判断某个字段或者表达式是否为null,如果是,则返回1,否则返回0
SELECT ISNULL(commission_pct), commission_pct FROM employees;

# like的使用不仅仅局限于字符型,数值型也可以使用
SELECT * FROM employees WHERE department_id LIKE '8%';

