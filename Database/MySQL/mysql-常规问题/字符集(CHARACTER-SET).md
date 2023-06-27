# 字符集(CHARACTER SET)

```shell script
# 查看Mysql支持的所有字符集:
SHOW CHARACTER SET;
# 国际上的UTF-8，在MySQL中，对标的是uft8mb4
# utf8这种字符集每个字符最多能占有3个字节，utf8mb4这种字符集的每个字符最多占有4个字节.(Maxlen就代表了每个字符最多占几个字节)
# utf8存储不了emoji表情,所以出现了utf8mb4
```

- 在utf8字符集下,一个英文字母占1个字节,一个中文字符占3个字节.
- 在gbk字符集下,一个英文字母占1个字节,一个中文字符占2个字节.

# 实际开发中utf8和utf8mb4到底选择哪个

    MySQL 的「utf8」实际上不是真正的 UTF-8。

「utf8」只支持每个字符最多三个字节，而真正的 UTF-8 是每个字符最多四个字节。MySQL 一直没有修复这个 bug，他们在 2010 年发布了一个叫作「utf8mb4」的字符集，绕过了这个问题。当然，他们并没有对新的字符集广而告之(可能是因为这个 bug 让他们觉得很尴尬），以致于现在网络上仍然在建议开发者使用「utf8」，但这些建议都是错误的。

    简单概括如下：
    
    1. MySQL 的「utf8mb4」是真正的「UTF-8」。
    2. MySQL 的「utf8」是一种「专属的编码」，它能够编码的 Unicode 字符并不多。

    我要在这里澄清一下：所有在使用「utf8」的 MySQL 和 MariaDB 用户都应该改用「utf8mb4」，永远都不要再使用「utf8」。

# 通过修改配置文件统一编码为utf8(实际开发中应该统一为utf8mb4)

    mysql装完之后字符集要马上统一,否则出现乱码之后,再改的话,改之前的数据依然会是乱码.(能解决改之后的数据不是乱码)

![](../pics/MySql配置文件.png)

![](../pics/Mysql客户端配置和服务端配置.png)

![](../pics/mysql06.png)

![](../pics/mysql07.png)

![](../pics/mysql08.png)

![](../pics/mysql10.png)

# windows下乱码如何解决

    当向 MySQL 数据库插入一条带有中文的数据时，会出现乱码，一切都是Windows的CMD的GBK搞的鬼，用Navicat直接插入就可以.
    
```mysql
SHOW VARIABLES LIKE 'char%'   
SET character_set_client = gbk;
SET character_set_results = gbk;
SET character_set_connection = gbk;
-- 或者直接这样,相当于完成以上三个设置
set names GBK;
```