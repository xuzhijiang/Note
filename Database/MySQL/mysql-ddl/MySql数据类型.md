# MySQL 数据类型

MySQL使用的多种数据类型可分为三类：数值、日期与时间，以及字符串类型

![](../pics/数据类型分类.png)
![](../pics/数据类型的选择原则.png)

# 数值类型

    MySQL使用标准的 ANSI SQL 数字类型

![](../pics/整型特点01.png)
![](../pics/整型特点02.png)
![](../pics/整型特点03.png)
![](../pics/小数.png)
![](../pics/定点数和浮点数.png)

---
    类型         字节     范围（有符号位）
    tinyint     1字节    可以有符号，也可以没有符号,有符号:-2^7~(2^7-1) 无符号位：0 ~ 2^8
    smallint    2字节    分为有无符号两种,取值范围分别是0~(2^16-1), (-2^15)~(2^15-1)
    mediumint   3字节    分为有无符号两种: 取值范围分别是0~(2^24-1), (-2^23)~(2^23-1)
    int/Integer 4字节    可以有符号，也可以没有符号。取值范围分别是0~(2^32-1), (-2^31)~(2^31-1)
    bigint      8字节    分为有无符号两种: 取值范围分别是0~(2^64-1), (-2^63)~(2^63-1)
    
    
    float(M,D)  4字节    不带符号的单精度浮点数,M 代表整数部门和小数部分的总长度，D 代表小数点后的位数。这两个参数都不是必需参数，它们默认为10, 2，表示小数点后有2位数字，而整个数字的位数为10（包含小数位数）,整数部分为8位,FLOAT 类型的小数精度可以达到24位.
    double(M,D) 8字节    不带符号的双精度浮点数。M 代表显示长度，D 代表小数点后的位数。这两个参数都不是必需参数，它们默认为16, 4，表示小数点后有4位数字，而整个数字的位数为 16（包含小数位数）,整数部分为12位。DOUBLE 类型的小数精度可以达到53位。DOUBLE 与 REAL 同义
    
    decimal(M, D)/(简写成dec(M,D))  非压缩的无符号浮点数。 在未压缩十进制中，每一位十进制数都对应一个字节。 M也表示总位数，D表示小数位数。需要定义显示长度（M）和小数位数（D）。DECIMAL 与 NUMERIC 同义。 保存一个精确的数值，不会发生数据的改变，不同于浮点数的四舍五入。将浮点数转换为字符串来保存，每9位数字保存为4个字节
---

>MySQL没有布尔类型，在SQL标准中，存在bool和boolean类型。在MySQL中创建表的时候也可以定义这两种类型，但是这两种类型最终转换成的是TINYINT（1）类型,所以在MySQL中，布尔类型等价于TINYINT(1)。因此创建表时定义的bool和Boolean类型，数据库中真实存储的是TINYINT(1).其中存0表示false.

# 字符串类型

![](../pics/字符类型.png)

---
    char(M)   固定长度的字符串，长度范围从1~255个字符，比如CHAR(5)。在存储时，会向右用空格补齐指定长度。长度并非必须参数，默认长度为1.
    
    VARCHAR(M) 不定长度的字符串，长度范围从1~255个字符。比如：CHAR(25)。在创建VARCHAR字段时，必须定义长度
    
    BLOB or TEXT 最大长度为65535个字符的字段。BLOB是Binary Large Objects（二进制大型对象）的缩写，专用于保存大量的二进制数据，比如图片或其他类型的文件。TEXT 类型的文件也能保存大型数据(非二进制字符串-字符字符串),这两者的区别在于存储数据的排序和对比方面，BLOB类型数据是大小写敏感的，而TEXT类型数据则不是。另外，不能指定它们的长度,text 类型在定义时，不可给默认值.
    
    BLOB分为: TINYBLOB,BLOB,MEDIUMBLOB,LONGBLOB.
    text分为: tinytext, text, mediumtext, longtext
    
    TINYBLOB or TINYTEXT 最大长度为255个字符的 BLOB 或 TEXT 字段。同样也不能指定它们的长度。
    
    MEDIUMBLOB or MEDIUMTEXT 最大长度为16777215个字符的 BLOB 或 TEXT 字段。同样也不能指定它们的长度。
    
    LONGBLOB or LONGTEXT 最大长度为4294967295个字符的 BLOB 或 TEXT 字段。同样也不能指定它们的长度
---
    
# 日期时间类型

![](../pics/日期类型01.png)    
![](../pics/日期类型02.png)
    
---    
    datetime    按照 YYYY-MM-DD HH:MM:SS 格式组合显示的日期与时间
    date        YYYY-MM-DD 格式
    timestamp   
    time        时间  按照 HH:MM:SS 格式存储的时间
    YEAR(M)     年份  用2位或4位格式存储的时间。如果把长度定为2，比如说YEAR(2)，那么可以表示从1970年到2069年的这些年份（70-69）。如果把长度定为4，YEAR(4)，则可以表示从1901年到2155年。默认长度为4.
---

![](../pics/timestamp和datetime的区别.png)

```sql
CREATE TABLE dt_table(
	t1 DATETIME,
	t2 TIMESTAMP
);

INSERT INTO dt_table VALUES(NOW(), NOW());
SELECT * FROM dt_table;

SHOW VARIABLES LIKE 'time_zone';
SET time_zone='+9:00';
SELECT * FROM dt_table;
```

# 其他类型

![](../pics/枚举类型.png)
![](../pics/枚举类型例子.png)

![](../pics/Set类型.png)
![](../pics/Set类型举例.png)
