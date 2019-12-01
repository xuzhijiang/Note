# 条件查询

- 条件运算符: =,>,<,>=,<=,<>或者!=,不等于一般使用标准的<>
- 逻辑运算符: AND/OR/NOT <条件>:表示“不符合该条件”的记录.
- like/ BETWEEN: 在2个值之间,例如: BETWEEN 1 AND 20;包括1和20. /  IS NULL: 判断是否为NULL值(NULL 与 0、空字符串都不同) /判断是否为非null值: IS NOT NULL/in
- =或者<>不能用于判断null值.
- 安全等于 <=> ,这个也可以用于判断是否为null
- 要组合三个或者更多的条件，就需要用小括号()表示如何进行条件运算.
- 如果不加括号，条件运算按照NOT、AND、OR的优先级进行，即NOT优先级最高，其次是AND，最后是OR。可以使用 () 来决定优先级，使得优先级关系更清晰.

```sql
-- 字符串比较根据ASCII码，中文字符比较根据数据库设置
SELECT * FROM students WHERE score >= 80 OR name > 'abc';

-- 不是2班的学生
-- NOT条件NOT class_id = 2其实等价于class_id <> 2
SELECT * FROM students WHERE NOT class_id = 2;
SELECT * FROM students WHERE class_id <> 2;

-- 模糊查询
-- %表示任意字符,%表示0个到多个，_表示任意1个字符
SELECT * FROM students WHERE name LIKE '小%';
-- 有一种特殊情况,查询的字符串中正好有_怎么办,比如查询name中第二个字符是_的学生信息
select * from students where name like '_\_%'; -- 把_转义成\_
-- 或者更加标准的写法
select * from students where name like '_$_%' escape '$'; -- $是我任意指定的,表示$_是_的转义字符,你可以用其他的特殊字符

-- 使用DISTINCT，相同值只会出现一次。它作用于所有列，也就是说所有列的值都相同才算相同。
SELECT DISTINCT id, name, gender, score FROM student;

-- between and的使用
-- 包含临界值
-- 而且2个临界值不能调换顺序
select * from students where class_id between 1 and 3;
-- class_id不在1到3之间
select * from students where class_id not between 1 and 3;
```